package com.diyiliu.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: HomeController
 * Author: DIYILIU
 * Update: 2018-04-26 22:46
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){

        return "index";
    }
}
