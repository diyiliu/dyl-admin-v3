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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;
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

    @GetMapping("/assetTree/{roleId}")
    public List assetTree(@PathVariable("roleId") long roleId) {
        List<SysPrivilege> privilegeList = sysPrivilegeJpa.findByMasterAndMasterValue("role", roleId);
        List<Long> list = privilegeList.stream().map(SysPrivilege::getAccessValue).collect(Collectors.toList());

        List<SysAsset> assetList = sysAssetJpa.findAll(new Sort(Sort.Direction.ASC, new String[]{"pid", "sort"}));
        // 根节点
        List<SysAsset> rootList = assetList.stream().filter(a -> a.getPid() == 0).collect(Collectors.toList());
        // 子节点
        Map<Long, List<SysAsset>> menuMap = assetList.stream().filter(a -> a.getPid() > 0)
                .collect(Collectors.groupingBy(SysAsset::getPid));

        List treeList = new ArrayList();
        for (SysAsset asset : rootList) {
            treeList.add(toTree("home", asset, menuMap, list));
        }

        return treeList;
    }

    /**
     * 转为树形结构数据
     *
     * @param code
     * @param sysAsset
     * @param menuMap
     * @return
     */
    private Map toTree(String code, SysAsset sysAsset, Map<Long, List<SysAsset>> menuMap, List<Long> ownList) {
        Map map = sysAsset.toTreeItem(code, ownList);

        long id = sysAsset.getId();
        if (menuMap.containsKey(id)) {
            map.put("open", 1);
            map.put("checkbox", "hidden");

            List items = new ArrayList();
            List<SysAsset> list = menuMap.get(id);
            for (SysAsset asset : list) {
                String parentCode = sysAsset.getCode();

                items.add(asset.toTreeItem(parentCode, ownList));
                toTree(parentCode, asset, menuMap, ownList);
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
    public Integer saveUser(SysUser user, @RequestParam("roleId") Long roleId) {
        Subject subject = SecurityUtils.getSubject();

        // 默认密码
        user.setPassword("123456");
        passwordHelper.encryptPassword(user);
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setExpireTime(DateUtil.stringToDate(user.getExpireTimeStr()));
        user.setCreateUser((String) subject.getPrincipal());

        SysRole role = new SysRole();
        role.setId(roleId);
        user.setRoles(new ArrayList(){
            {
                this.add(role);
            }
        });

        user = sysUserJpa.save(user);
        if (user == null) {

            return 0;
        }

        return 1;
    }

    @PutMapping("/user")
    public Integer modifyUser(SysUser user, @RequestParam("roleId") Long roleId) {
        SysUser temp = sysUserJpa.findOne(user.getId());

        temp.setName(user.getName());
        temp.setTel(user.getTel());
        temp.setEmail(user.getEmail());
        temp.setExpireTime(DateUtil.stringToDate(user.getExpireTimeStr()));
        if (roleId != null){
            SysRole role = new SysRole();
            role.setId(roleId);
            temp.setRoles(new ArrayList(){
                {
                    this.add(role);
                }
            });
        }

        temp = sysUserJpa.save(temp);
        if (temp == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @DeleteMapping("/user")
    public Integer deleteUser(@RequestBody List<Long> ids) {
        sysUserJpa.deleteByIdIn(ids);

        return 1;
    }

    /**
     * 密码重置
     *
     * @param userId
     * @return
     */
    @PutMapping("/userPwd/{id}")
    public Integer resetPwd(@PathVariable("id") Long userId) {
        SysUser temp = sysUserJpa.findOne(userId);
        temp.setPassword("123456");
        passwordHelper.encryptPassword(temp);

        temp = sysUserJpa.save(temp);
        if (temp == null) {

            return 0;
        }

        return 1;
    }


    @PutMapping("/userPwd")
    public Map modifyPwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd,
                         HttpSession session) {
        SysUser current = (SysUser) session.getAttribute("user");
        SysUser temp = sysUserJpa.findOne(current.getId());

        Map respMap = new HashMap();

        String enPwd = passwordHelper.encryptPassword(temp.getUsername(), oldPwd, temp.getSalt());
        if (enPwd.equals(temp.getPassword())){
            temp.setPassword(newPwd);
            passwordHelper.encryptPassword(temp);

            temp = sysUserJpa.save(temp);
            if (temp == null){

                respMap.put("result", "0");
                respMap.put("msg", "修改密码失败!");

            }else {
                respMap.put("result", "1");
                respMap.put("msg", "修改密码成功!");
            }

            return respMap;
        }

        respMap.put("result", "0");
        respMap.put("msg", "原密码错误!");

        return respMap;
    }


    @PostMapping("/roleList")
    public List<SysRole> roleList() {

        return sysRoleJpa.findAll();
    }

    @PostMapping("/role")
    public Integer addRole(SysRole role) {
        Subject subject = SecurityUtils.getSubject();
        role.setCreateTime(new Date());
        role.setCreateUser((String) subject.getPrincipal());

        role = sysRoleJpa.save(role);
        if (role == null) {

            return 0;
        }

        return 1;
    }

    @PutMapping("/role")
    public Integer modifyRole(SysRole role) {
        SysRole oldRole = sysRoleJpa.findOne(role.getId());
        oldRole.setName(role.getName());
        oldRole.setCode(role.getCode());
        oldRole.setComment(role.getComment());

        role = sysRoleJpa.save(oldRole);
        if (role == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @DeleteMapping("/role")
    public Integer deleteRole(@RequestBody List<Long> ids) {
        sysRoleJpa.deleteByIdIn(ids);

        return 1;
    }


    @Transactional
    @PostMapping("/roleAsset")
    public Integer roleAsset(@RequestBody List<SysPrivilege> privilegeList) {
        long roleId = privilegeList.get(0).getMasterValue();

        // 删除
        sysPrivilegeJpa.deleteByMasterAndMasterValue("role", roleId);

        // 添加
        privilegeList = sysPrivilegeJpa.save(privilegeList);
        if (privilegeList == null) {

            return 0;
        }

        return 1;
    }
}
