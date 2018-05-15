package com.diyiliu.web.guide.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Description: SiteType
 * Author: DIYILIU
 * Update: 2017-10-19 15:18
 */

@Entity
@Table(name = "guide_type")
public class SiteType {

    @Id
    @GeneratedValue
    private int id;

    private String code;

    private String name;

    private int sort;

    @JsonIgnore
    @OrderBy("top asc ")
    @OneToMany(mappedBy = "siteType")
    private List<Website> siteList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Website> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<Website> siteList) {
        this.siteList = siteList;
    }
}
