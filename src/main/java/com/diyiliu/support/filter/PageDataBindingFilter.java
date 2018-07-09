package com.diyiliu.support.filter;

import com.diyiliu.support.model.BuoyInfo;
import com.diyiliu.support.util.JacksonUtil;
import com.diyiliu.web.guide.dto.SiteType;
import com.diyiliu.web.guide.facade.SiteTypeJpa;
import com.diyiliu.web.sys.dto.SysRole;
import com.diyiliu.web.sys.dto.SysUser;
import com.diyiliu.web.sys.facade.SysRoleJpa;
import com.diyiliu.web.sys.facade.SysUserJpa;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: PageDataBindingFilter
 * Author: DIYILIU
 * Update: 2018-05-11 10:14
 */

@Aspect
@Component
public class PageDataBindingFilter {

    @Resource
    private SysRoleJpa sysRoleJpa;

    @Resource
    private SysUserJpa sysUserJpa;

    @Resource
    private SiteTypeJpa siteTypeJpa;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private Environment environment;

    @After("execution(* com.diyiliu.web.home.HomeController.show(..))")
    public void doAfter(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Object[] args = joinPoint.getArgs();
        String menu = (String) args[0];

        if ("user".equals(menu)) {
            List<SysRole> roleList = sysRoleJpa.findAll();
            request.setAttribute("roles", roleList);

            List<SysUser> userList = sysUserJpa.findAll();
            List<String> usernameList = userList.stream().map(SysUser::getUsername).collect(Collectors.toList());
            List<String> nameList = userList.stream().map(SysUser::getName).collect(Collectors.toList());
            nameList.addAll(usernameList);
            request.setAttribute("names", nameList);

            return;
        }

        if (menu.startsWith("guide")) {
            Sort typeSort = new Sort(new Sort.Order[]{new Sort.Order("sort")});
            List<SiteType> siteTypes = siteTypeJpa.findAll(typeSort);
            request.setAttribute("types", siteTypes);

            // 主页面 添加chosen
            if (!menu.contains(".1")) {
                List<String> names = siteTypes.stream().map(SiteType::getName).collect(Collectors.toList());
                request.setAttribute("tNames", names);
            }

            return;
        }


        if ("buoy1".equals(menu)) {
            String url = environment.getProperty("buoy.server-path") + "/list";
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String json = responseEntity.getBody();

            try {
                List<BuoyInfo> dataInfoList = JacksonUtil.toList(json, BuoyInfo.class);
                List<String> buoys = dataInfoList.stream().filter(data -> data.getType().equals("3")).map(BuoyInfo::getName).collect(Collectors.toList());
                List<String> dummies = dataInfoList.stream().filter(data -> data.getType().equals("1")).map(BuoyInfo::getName).collect(Collectors.toList());

                request.setAttribute("buoys", buoys);
                request.setAttribute("dummies", dummies);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }
    }
}
