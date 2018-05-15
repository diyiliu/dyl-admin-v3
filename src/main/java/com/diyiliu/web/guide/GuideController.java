package com.diyiliu.web.guide;

import com.diyiliu.web.guide.dto.SiteType;
import com.diyiliu.web.guide.dto.Website;
import com.diyiliu.web.guide.facade.SiteTypeJpa;
import com.diyiliu.web.guide.facade.WebsiteJpa;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: GuideController
 * Author: DIYILIU
 * Update: 2018-05-15 21:08
 */

@Controller
public class GuideController {

    @Resource
    private WebsiteJpa websiteJpa;

    @Resource
    private SiteTypeJpa siteTypeJpa;


    @GetMapping("/")
    public String guide(Model model) {
        Sort typeSort = new Sort(new Sort.Order[]{new Sort.Order("top")});
        List<SiteType> siteTypes = siteTypeJpa.findAll(typeSort);

        List<SiteType> typeList = siteTypes.stream().filter(t -> CollectionUtils.isNotEmpty(t.getSiteList())).collect(Collectors.toList());
        model.addAttribute("typeList", typeList);

        return "guide";
    }

    @ResponseBody
    @PostMapping("/guide/list")
    public Map siteList(@RequestParam int pageNo, @RequestParam int pageSize,
                        @RequestParam(required = false) String search) {

        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "createTime")});
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);

        Page<Website> sitePage;
        if (StringUtils.isEmpty(search)) {
            sitePage = websiteJpa.findAll(pageable);
        } else {
            String like = "%" + search + "%";
            sitePage = websiteJpa.findByNameLikeOrUrlLike(like, like, pageable);
        }

        Map respMap = new HashMap();
        respMap.put("data", sitePage.getContent());
        respMap.put("total", sitePage.getTotalElements());

        return respMap;
    }
}
