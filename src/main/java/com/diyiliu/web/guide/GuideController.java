package com.diyiliu.web.guide;

import com.diyiliu.web.guide.dto.SiteType;
import com.diyiliu.web.guide.facade.SiteTypeJpa;
import com.diyiliu.web.guide.facade.WebsiteJpa;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
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
}
