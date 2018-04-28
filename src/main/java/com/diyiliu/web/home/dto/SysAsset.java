package com.diyiliu.web.home.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Description: SysAsset
 * Author: DIYILIU
 * Update: 2018-04-28 14:15
 */

@Data
@Entity
@Table(name = "sys_asset")
public class SysAsset {

    @Id
    @GeneratedValue
    private Long id;

    private Long pid;

    private String name;

    private String type;

    private String controller;

    private String action;

    private String iconCss;

    private Integer isMenu;

    private Integer sort;

    @Transient
    private List<SysAsset> children;

    @Transient
    private Integer active;
}
