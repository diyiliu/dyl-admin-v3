package com.diyiliu.support.shiro.matcher;

import com.diyiliu.web.sys.dto.SysUser;
import com.diyiliu.web.sys.facade.SysUserJpa;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: RetryCredentialsMatcher
 * Author: DIYILIU
 * Update: 2018-05-03 13:31
 */

public class RetryCredentialsMatcher extends HashedCredentialsMatcher {

    private CacheManager cacheManager;

    private SysUserJpa sysUserJpa;

    public RetryCredentialsMatcher(CacheManager cacheManager, SysUserJpa sysUserJpa) {
        this.cacheManager = cacheManager;
        this.sysUserJpa = sysUserJpa;
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
            //clear retry count
            passwordRetryCache.remove(username);

            // 清除浏览器缓存 重新注入
            Session session = SecurityUtils.getSubject().getSession();
            if (session.getAttribute("user") == null){
                SysUser user = sysUserJpa.findByUsername(username);
                session.setAttribute("user", user);
            }
        }
        return matches;
    }
}
