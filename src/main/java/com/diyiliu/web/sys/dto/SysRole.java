package com.diyiliu.web.sys.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: SysRole
 * Author: DIYILIU
 * Update: 2018-05-02 22:34
 */

@Data
@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    @GeneratedValue
    private Long id;

    private Long pid;

    private String name;

    private String code;

    private String comment;
}
