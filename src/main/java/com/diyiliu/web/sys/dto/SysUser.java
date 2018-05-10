package com.diyiliu.web.sys.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Description: SysUser
 * Author: DIYILIU
 * Update: 2018-05-02 22:11
 */

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String salt;

    private String name;

    private String tel;

    private String email;

    @ManyToMany
    @JoinTable(name = "rel_user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<SysRole> roles;

    private Long orgId;

    private Date createTime;

    private String createUser;

    private Integer status;

    private Date expireTime;

    private Integer loginCount;

    private String lastLoginIp;

    private Date lastLoginTime;

    @Transient
    private String expireTimeStr;

    public String getCredentialsSalt() {
        return username + salt;
    }
}
