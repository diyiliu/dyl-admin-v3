package com.diyiliu.web.guide;

import com.diyiliu.support.config.properties.UploadProperties;
import com.diyiliu.web.guide.dto.SiteType;
import com.diyiliu.web.guide.dto.Website;
import com.diyiliu.web.guide.facade.SiteTypeJpa;
import com.diyiliu.web.guide.facade.WebsiteJpa;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Description: GuideController
 * Author: DIYILIU
 * Update: 2018-05-15 21:08
 */


@Slf4j
@RestController
@RequestMapping("/guide")
@EnableConfigurationProperties(UploadProperties.class)
public class GuideController {

    @Resource
    private WebsiteJpa websiteJpa;

    @Resource
    private SiteTypeJpa siteTypeJpa;

    @Resource
    private UploadProperties uploadProperties;

    @Resource
    private ResourceLoader resourceLoader;

    @PostMapping("/list")
    public Map siteList(@RequestParam int pageNo, @RequestParam int pageSize,
                        @RequestParam(required = false) String search, @RequestParam(required = false) Long typeId) {
        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "createTime")});
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);

        Page<Website> sitePage;
        if (StringUtils.isEmpty(search) && typeId == null) {
            sitePage = websiteJpa.findAll(pageable);
        } else {
            sitePage = websiteJpa.findAll(
                    (Root<Website> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                        Path<String> nameExp = root.get("name");
                        Path<String> urlExp = root.get("url");
                        Path<SiteType> typeExp = root.get("siteType");

                        Predicate predicate = null;
                        if (StringUtils.isNotEmpty(search)) {
                            String like = "%" + search + "%";
                            predicate = cb.or(new Predicate[]{cb.like(nameExp, like), cb.like(urlExp, like)});
                        }

                        if (typeId != null) {
                            predicate = cb.and(new Predicate[]{predicate, cb.equal(typeExp, new SiteType(typeId))});
                        }

                        return predicate;
                    }, pageable);
        }

        Map respMap = new HashMap();
        respMap.put("data", sitePage.getContent());
        respMap.put("total", sitePage.getTotalElements());

        return respMap;
    }

    @PostMapping("/site")
    public Integer saveSite(Website website) throws Exception {
        website.setCreateTime(new Date());

        // 抓取图片
        byte[] imgBytes = fetchICO(website.getUrl());
        if (imgBytes == null) {
            website.setImage("unknown.png");
        } else {
            File tempFile = File.createTempFile("icon", ".png", uploadProperties.getImagePath().getFile());
            FileCopyUtils.copy(imgBytes, tempFile);
            website.setImage(tempFile.getName());
        }

        website = websiteJpa.save(website);
        if (website == null) {

            return 0;
        }

        return 1;
    }

    @PutMapping("/site")
    public Integer modifySite(Website website) {
        Website temp = websiteJpa.findById(website.getId());

        website.setImage(temp.getImage());
        website.setCreateTime(temp.getCreateTime());
        website = websiteJpa.save(website);
        if (website == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @DeleteMapping("/site")
    public Integer deleteSite(@RequestBody List<Long> ids) throws IOException {
        List<Website> list = websiteJpa.findByIdIn(ids);

        // 删除文件
        for (Website site : list) {
            String imgPath = site.getImage();
            if (StringUtils.isEmpty(imgPath) || "unknown.png".equals(imgPath)) {
                continue;
            }
            org.springframework.core.io.Resource localRes = getLocalResource(site.getImage());
            if (!localRes.getFile().delete()) {
                log.error("删除文件[{}]失败!");
            }
        }

        // 删除数据
        websiteJpa.delete(list);

        return 1;
    }


    @Transactional
    @PutMapping("/type")
    public Integer modifySiteType(@RequestParam("typeNames") String typeNames) {
        String[] names = typeNames.split(",");
        List<String> nameList = Arrays.asList(names);

        List<SiteType> siteTypes = siteTypeJpa.findAll();
        List<String> oldList = siteTypes.stream().map(SiteType::getName).collect(Collectors.toList());

        // 新增
        List<String> addTemps = (List<String>) CollectionUtils.subtract(nameList, oldList);
        List<SiteType> list = new ArrayList();
        for (String temp : addTemps) {
            list.add(new SiteType(temp));
        }

        list = siteTypeJpa.save(list);
        if (list == null) {
            return 0;
        }

        // 删除
        List<String> delTemps = (List<String>) CollectionUtils.subtract(oldList, nameList);
        siteTypeJpa.deleteByNameIn(delTemps);

        return 1;
    }


    @RequestMapping(value = "/image/{name:.+}")
    public void uploadedImage(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        org.springframework.core.io.Resource imgPath;
        if ("unknown.png".equals(name)) {
            imgPath = uploadProperties.getUnknownImg();
        } else {
            imgPath = getLocalResource(name);
        }

        if (imgPath != null) {
            response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(imgPath.getFilename()));
            FileCopyUtils.copy(imgPath.getInputStream(), response.getOutputStream());
        }
    }


    /**
     * 获取图片资源
     *
     * @param name
     * @return
     * @throws IOException
     */
    private org.springframework.core.io.Resource getLocalResource(String name) throws IOException {
        String path = uploadProperties.getImagePath().getURL().getPath();

        return resourceLoader.getResource("file:" + Paths.get(path, name).toString());
    }

    /**
     * 抓取网站图标
     *
     * @param location
     * @return
     */
    private byte[] fetchICO(String location) {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36";
        String scheme = "http";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, userAgent);

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                scheme + "://" + location,
                HttpMethod.GET,
                new HttpEntity<byte[]>(headers),
                byte[].class);

        int statusCode = responseEntity.getStatusCode().value();
        if (301 == statusCode || 302 == statusCode) {
            URI uri = responseEntity.getHeaders().getLocation();
            scheme = uri.getScheme();
            location = uri.getHost();

            responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<byte[]>(headers),
                    byte[].class);
            statusCode = responseEntity.getStatusCode().value();
        }

        String icoPath = null;
        try {
            if (200 == statusCode) {
                InputStream inputStream = new ByteArrayInputStream(responseEntity.getBody());
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains(".ico")) {
                        // 取出有用的范围
                        Pattern p = Pattern.compile(".*<link[^>]*href=\"(?<href>[^\"]*)\"[^>]*>");
                        Matcher m = p.matcher(line.trim());
                        if (m.matches()) {
                            String path = m.group(1);
                            if (!path.contains("//")) {
                                path = scheme + "://" + location + path;
                            }
                            if (!path.startsWith("http")) {
                                path = scheme + ":" + path;
                            }
                            icoPath = path;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (icoPath == null) {
            icoPath = "http://statics.dnspod.cn/proxy_favicon/_/favicon?domain=" + location;
        }
        responseEntity = restTemplate.exchange(
                icoPath,
                HttpMethod.GET,
                new HttpEntity<byte[]>(headers),
                byte[].class);

        statusCode = responseEntity.getStatusCode().value();
        if (statusCode == 200) {
            byte[] bytes = responseEntity.getBody();
            // 默认图标
            if (bytes.length != 726) {
                return bytes;
            }
        }
        return null;
    }
}
