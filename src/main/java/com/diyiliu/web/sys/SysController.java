package com.diyiliu.web.sys;

import com.diyiliu.support.shiro.helper.PasswordHelper;
import com.diyiliu.support.util.DateUtil;
import com.diyiliu.web.sys.dto.SysAsset;
import com.diyiliu.web.sys.dto.SysPrivilege;
import com.diyiliu.web.sys.dto.SysRole;
import com.diyiliu.web.sys.dto.SysUser;
import com.diyiliu.web.sys.facade.SysAssetJpa;
import com.diyiliu.web.sys.facade.SysPrivilegeJpa;
import com.diyiliu.web.sys.facade.SysRoleJpa;
import com.diyiliu.web.sys.facade.SysUserJpa;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private SysRoleJpa sysRoleJpa;

    @Resource
    private SysPrivilegeJpa sysPrivilegeJpa;

    @Resource
    private PasswordHelper passwordHelper;


    @PostMapping("/assetList")
    public List<SysAsset> assetList() {

        return sysAssetJpa.findAll();
    }

    @GetMapping("/assetTree")
    public List assetTree() {
        List<SysAsset> assetList = sysAssetJpa.findAll(new Sort(Sort.Direction.ASC, new String[]{"pid", "sort"}));

        // 根节点
        List<SysAsset> rootList = assetList.stream().filter(a -> a.getPid() == 0).collect(Collectors.toList());
        // 子节点
        Map<Long, List<SysAsset>> menuMap = assetList.stream().filter(a -> a.getPid() > 0)
                .collect(Collectors.groupingBy(SysAsset::getPid));

        List treeList = new ArrayList();
        for (SysAsset asset : rootList) {
            treeList.add(toTree(asset, menuMap));
        }

        return treeList;
    }

    /**
     * 转为树形结构数据
     *
     * @param sysAsset
     * @param menuMap
     * @return
     */
    private Map toTree(SysAsset sysAsset, Map<Long, List<SysAsset>> menuMap) {
        Map map = sysAsset.toTreeItem();

        long id = sysAsset.getId();
        if (menuMap.containsKey(id)) {
            map.put("open", 1);
            map.put("checkbox", "hidden");

            List items = new ArrayList();
            List<SysAsset> list = menuMap.get(id);
            for (SysAsset asset : list) {
                items.add(asset.toTreeItem());

                toTree(asset, menuMap);
            }
            map.put("items", items);
        }

        return map;
    }

    @PostMapping("/userList")
    public List<SysUser> userList() {
        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "lastLoginTime")});

        return sysUserJpa.findAll(sort);
    }

    @PostMapping("/user")
    public Integer saveUser(SysUser user) {
        Subject subject = SecurityUtils.getSubject();

        // 默认密码
        user.setPassword("123456");
        passwordHelper.encryptPassword(user);
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setExpireTime(DateUtil.stringToDate(user.getExpireTimeStr()));
        user.setCreateUser((String) subject.getPrincipal());

        user = sysUserJpa.save(user);
        if (user == null) {

            return 0;
        }

        return 1;
    }


    @PostMapping("/roleList")
    public List<SysRole> roleList() {

        return sysRoleJpa.findAll();
    }

    @PostMapping("/role")
    public Integer saveRole(SysRole role) {
        Subject subject = SecurityUtils.getSubject();

        role.setCreateTime(new Date());
        role.setCreateUser((String) subject.getPrincipal());

        role = sysRoleJpa.save(role);
        if (role == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @PostMapping("/roleAsset")
    public Integer roleAsset(@RequestBody List<SysPrivilege> privilegeList) {
        long roleId = privilegeList.get(0).getMasterValue();

        // 删除
        sysPrivilegeJpa.deleteByMasterAndMasterValue("role", roleId);

        List list = privilegeList.stream().map(SysPrivilege::getAccessValue).collect(Collectors.toList());
        List privileges = sysPrivilegeJpa.findByAssetIdIn(list);
        for (int i = 0; i < privilegeList.size(); i++) {
            SysPrivilege sysPrivilege = privilegeList.get(i);

            Object[] objArr = (Object[]) privileges.get(i);
            sysPrivilege.setAccess((String) objArr[1]);
            sysPrivilege.setPermission((String) objArr[0]);
        }

        // 添加
        privilegeList = sysPrivilegeJpa.save(privilegeList);
        if (privilegeList == null) {

            return 0;
        }

        return 1;
    }
}
