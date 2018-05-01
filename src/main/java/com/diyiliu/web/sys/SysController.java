package com.diyiliu.web.sys;

import com.diyiliu.web.sys.dto.SysAsset;
import com.diyiliu.web.sys.facade.SysAssetJpa;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: SysController
 * Author: DIYILIU
 * Update: 2018-05-01 22:39
 */

@RestController
@RequestMapping("/sys")
public class SysController {

    @Resource
    private SysAssetJpa sysAssetJpa;

    @PostMapping("/assetList")
    public List<SysAsset> assetList(){

        return sysAssetJpa.findAll();
    }

}
