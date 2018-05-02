package com.diyiliu.support.shiro.realm;

import com.diyiliu.web.account.dto.SysUser;
import com.diyiliu.web.account.facade.SysUserJpa;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * Description: UserRealm
 * Author: DIYILIU
 * Update: 2017-11-24 10:50
 */

public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUserJpa sysUserJpa;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        SysUser user = sysUserJpa.findByUsername(username);

        if (user == null) {
            // 找不到用户
            throw new UnknownAccountException();
        }

        /*if (Boolean.TRUE.equals(user.getLocked())) {

            // 用户锁定
            throw new LockedAccountException();
        }*/

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName());

        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();

/*        List<Role> roleList = userService.findUserRoles(username);
        Set<String> roleCodes = roleList.stream().map(Role::getRoleCode).collect(Collectors.toSet());
        Set<Long> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toSet());

        List<Privilege> privilegeList = userService.findPrivileges("role", roleIds);
        Set<String> permissions = privilegeList.stream().map(Privilege::getPermission).collect(Collectors.toSet());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleCodes);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;*/

        return null;
    }
}
