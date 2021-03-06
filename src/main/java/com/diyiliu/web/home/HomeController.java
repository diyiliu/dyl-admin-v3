package com.diyiliu.web.home;

import com.diyiliu.web.guide.dto.SiteType;
import com.diyiliu.web.guide.facade.SiteTypeJpa;
import com.diyiliu.web.sys.dto.SysAsset;
import com.diyiliu.web.sys.facade.SysAssetJpa;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: HomeController
 * Author: DIYILIU
 * Update: 2018-04-26 22:46
 */

@Controller
public class HomeController {

    @Resource
    private SysAssetJpa sysAssetJpa;

    @Resource
    private SiteTypeJpa siteTypeJpa;


    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");

        if (UnknownAccountException.class.getName().equals(exceptionClassName)
                || IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {

            redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
            return "redirect:/login";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {

            redirectAttributes.addFlashAttribute("error", "登录错误次数超限，请稍后再试！");
            return "redirect:/login";

        } else if (exceptionClassName != null) {
            redirectAttributes.addFlashAttribute("error", "登录异常：" + exceptionClassName);

            return "redirect:/login";
        }

        SysAsset asset = sysAssetJpa.findByCode("index");
        request.getSession().setAttribute("active", asset);

        return "index";
    }

    @GetMapping("/home")
    public String index(HttpSession session) {
        SysAsset asset = sysAssetJpa.findByCode("index");
        session.setAttribute("active", asset);

        return "index";
    }

    @GetMapping("/home/{menu:.+}")
    public String show(@PathVariable("menu") String menu, HttpSession session) {
        SysAsset asset = sysAssetJpa.findByController("home/" + menu);
        if (asset == null) {
            return "error/404";
        }
        String view = asset.getView();

        // 菜单页面的子页面
        if (menu.contains(".")) {
            menu = menu.split("\\.")[0];
            asset = sysAssetJpa.findByController("home/" + menu);

            if (asset == null) {
                return "error/404";
            }
        }

        // 设置当前页
        session.setAttribute("active", asset);

        return view;
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();

        return "redirect:/login";
    }


    @GetMapping("/")
    public String guide(Model model) {
        Sort typeSort = new Sort(new Sort.Order[]{new Sort.Order("sort")});
        List<SiteType> siteTypes = siteTypeJpa.findAll(typeSort);

        List<SiteType> typeList = siteTypes.stream().filter(t -> CollectionUtils.isNotEmpty(t.getSiteList())).collect(Collectors.toList());
        model.addAttribute("typeList", typeList);

        return "guide";
    }
}
