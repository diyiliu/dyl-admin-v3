package com.diyiliu.web.sys.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Description: SysPrivilege
 * Author: DIYILIU
 * Update: 2018-05-02 22:44
 */
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
