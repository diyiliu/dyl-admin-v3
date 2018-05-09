package com.diyiliu.web.sys.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String code;
    
    private String type;

    /** 控制器(路径) */
    private String controller;

    /** 视图 */
    private String view;

    private String iconCss;

    /** 是否为菜单资源 0: 否; 1: 是*/
    private Integer isMenu;

    private Integer sort;

    @Transient
    private List<SysAsset> children;

    public Map toTreeItem(){
        Map map = new HashMap();
        map.put("id", id);
        map.put("text", name);

        return map;
    }
}
