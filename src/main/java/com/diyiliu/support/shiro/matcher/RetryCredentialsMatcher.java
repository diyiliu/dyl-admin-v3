package com.diyiliu.support.shiro.matcher;

import com.diyiliu.web.sys.dto.SysAsset;
import com.diyiliu.web.sys.dto.SysPrivilege;
import com.diyiliu.web.sys.dto.SysRole;
import com.diyiliu.web.sys.dto.SysUser;
import com.diyiliu.web.sys.facade.SysAssetJpa;
import com.diyiliu.web.sys.facade.SysPrivilegeJpa;
import com.diyiliu.web.sys.facade.SysUserJpa;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Description: RetryCredentialsMatcher
 * Author: DIYILIU
 * Update: 2018-05-03 13:31
 */

public class RetryCredentialsMatcher extends HashedCredentialsMatcher {

    private CacheManager cacheManager;

    private SysUserJpa sysUserJpa;

    private SysPrivilegeJpa sysPrivilegeJpa;

    private SysAssetJpa sysAssetJpa;

    public RetryCredentialsMatcher(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");

        String username = (String) token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // 清除重试次数
            passwordRetryCache.remove(username);

            // 装载数据
            loadData(username);
        }

        return matches;
    }

    /**
     * 初始化菜单
     *
     * @param username
     */
    private void loadData(String username){
        // 把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        SysUser user = sysUserJpa.findByUsername(username);

        List<SysRole> roleList = user.getRoles();
        Set roles = roleList.stream().map(SysRole::getId).collect(Collectors.toSet());

        List<SysPrivilege> privilegeList = sysPrivilegeJpa.findByMasterAndMasterValueIn("role", roles);
        Set assets = privilegeList.stream().map(SysPrivilege::getAccessValue).collect(Collectors.toSet());
        Set nodes = sysAssetJpa.findByChildren(assets);
        // 查询出的结果为Integer, 需要转Long
        nodes.forEach(e -> assets.add(Long.parseLong(String.valueOf(e))));

        // 菜单节点
        List<SysAsset> assetList = sysAssetJpa.findByIsMenuAndIdInOrderByPidAscSortAsc(1, assets);
        // 根节点
        List<SysAsset> rootList = assetList.stream().filter(a -> a.getPid() == 0).collect(Collectors.toList());
        // 子节点
        Map<Long, List<SysAsset>> menuMap = assetList.stream().filter(a -> a.getPid() > 0).collect(Collectors.groupingBy(SysAsset::getPid));
        for (SysAsset asset: rootList){
            long id = asset.getId();
            if ("node".equals(asset.getType()) || menuMap.containsKey(id)){
                List<SysAsset> children = menuMap.get(id);
                if (CollectionUtils.isNotEmpty(children)){
                    asset.setChildren(children);
                }
            }
        }

        session.setAttribute("user", user);
        // 初始化菜单
        session.setAttribute("menus", rootList);
    }

    public void setSysUserJpa(SysUserJpa sysUserJpa) {
        this.sysUserJpa = sysUserJpa;
    }

    public void setSysPrivilegeJpa(SysPrivilegeJpa sysPrivilegeJpa) {
        this.sysPrivilegeJpa = sysPrivilegeJpa;
    }

    public void setSysAssetJpa(SysAssetJpa sysAssetJpa) {
        this.sysAssetJpa = sysAssetJpa;
    }
}
