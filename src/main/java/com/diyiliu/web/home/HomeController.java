package com.diyiliu.web.home;

import com.diyiliu.web.home.dto.SysAsset;
import com.diyiliu.web.home.facade.SysAssetJpa;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/")
    public String index(HttpSession session){
//        List<SysAsset> assetList = sysAssetJpa.findAll(Sort.by(new String[]{"pid, sort"}));

        List<SysAsset> assetList = sysAssetJpa.findByTypeInOrderByPidAscSortAsc(new String[]{"node", "menu"});
        // 菜单根节点
        List<SysAsset> rootList = assetList.stream().filter(a -> a.getPid() == null).collect(Collectors.toList());

        // 菜单子节点
        Map<Long, List<SysAsset>> menuMap = assetList.stream().filter(a -> a.getPid() != null)
                .collect(Collectors.groupingBy(SysAsset::getPid));

        for (SysAsset asset: rootList){
            long id = asset.getId();
            if ("node".equals(asset.getType()) || menuMap.containsKey(id)){
                List<SysAsset> children = menuMap.get(id);
                if (CollectionUtils.isNotEmpty(children)){
                    asset.setChildren(children);
                }
            }
        }

        rootList.get(0).setActive(1);
        session.setAttribute("menus", rootList);
        return "index";
    }
}
