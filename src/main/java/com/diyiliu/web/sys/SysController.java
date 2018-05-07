package com.diyiliu.web.sys;

import com.diyiliu.web.sys.dto.SysAsset;
import com.diyiliu.web.sys.dto.SysUser;
import com.diyiliu.web.sys.facade.SysAssetJpa;
import com.diyiliu.web.sys.facade.SysUserJpa;
import org.springframework.data.domain.Sort;
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

    @Resource
    private SysUserJpa sysUserJpa;


    @PostMapping("/assetList")
    public List<SysAsset> assetList() {

        return sysAssetJpa.findAll();
    }

    @PostMapping("/userList")
    public List<SysUser> userList() {
        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "lastLoginTime")});

        return sysUserJpa.findAll(sort);
    }

}
