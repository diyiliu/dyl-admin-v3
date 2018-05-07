package com.diyiliu.support.shiro.realm;

import com.diyiliu.web.sys.dto.SysUser;
import com.diyiliu.web.sys.facade.SysUserJpa;
import com.diyiliu.web.sys.dto.SysPrivilege;
import com.diyiliu.web.sys.dto.SysRole;
import com.diyiliu.web.sys.facade.SysPrivilegeJpa;
import com.diyiliu.web.sys.facade.SysRoleJpa;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: UserRealm
 * Author: DIYILIU
 * Update: 2017-11-24 10:50
 */

public class UserRealm extends AuthorizingRealm {
    
    private SysUserJpa sysUserJpa;

    private SysRoleJpa sysRoleJpa;
    
    private SysPrivilegeJpa sysPrivilegeJpa;
    
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
        List<SysRole> roleList = sysRoleJpa.findByUser(username);

        Set<String> codes = new HashSet();
        Set<Long> roles = new HashSet();
        for (SysRole role: roleList){
            roles.add(role.getId());
            codes.add(role.getCode());
        }
        List<SysPrivilege> privilegeList = sysPrivilegeJpa.findByMasterAndMasterValueIn("role", roles);
        Set<String> permissions = privilegeList.stream().map(SysPrivilege::getPermission).collect(Collectors.toSet());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(codes);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    public void setSysUserJpa(SysUserJpa sysUserJpa) {
        this.sysUserJpa = sysUserJpa;
    }

    public void setSysRoleJpa(SysRoleJpa sysRoleJpa) {
        this.sysRoleJpa = sysRoleJpa;
    }

    public void setSysPrivilegeJpa(SysPrivilegeJpa sysPrivilegeJpa) {
        this.sysPrivilegeJpa = sysPrivilegeJpa;
    }
}
