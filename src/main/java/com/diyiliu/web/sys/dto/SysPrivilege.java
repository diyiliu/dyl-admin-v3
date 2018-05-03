package com.diyiliu.web.sys.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: SysPrivilege
 * Author: DIYILIU
 * Update: 2018-05-02 22:44
 */

@Data
@Entity
@Table(name = "sys_privilege")
public class SysPrivilege {

    @Id
    @GeneratedValue
    private Long id;

    private String master;

    private Long masterValue;

    private String access;

    private Long accessValue;

    private String permission;

    private String comment;
}
