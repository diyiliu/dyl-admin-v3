package com.diyiliu.web.home;

import com.diyiliu.web.sys.dto.SysAsset;
import com.diyiliu.web.sys.facade.SysAssetJpa;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description: HomeController
 * Author: DIYILIU
 * Update: 2018-04-26 22:46
 */

@Controller
public class HomeController {

    @Resource
    private SysAssetJpa sysAssetJpa;

    
    @GetMapping("/login")
    public String login(){

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

        return "home";
    }

    @GetMapping("/")
    public String index(HttpSession session){
        SysAsset asset = sysAssetJpa.findByCode("index");
        session.setAttribute("active", asset);

        return "index";
    }

    @GetMapping("/home/{menu}")
    public String show(@PathVariable("menu") String menu, HttpSession session){
        SysAsset asset = sysAssetJpa.findByController("home/" + menu);
        session.setAttribute("active", asset);

        return asset.getView();
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();

        return "redirect:/login";
    }
}
