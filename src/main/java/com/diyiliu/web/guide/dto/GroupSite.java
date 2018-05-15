package com.diyiliu.web.guide.dto;

import lombok.Data;

import java.util.List;

/**
 * Description: GroupSite
 * Author: DIYILIU
 * Update: 2017-10-20 11:33
 */

@Data
public class GroupSite {

    private String type;
    private List<Website> websiteList;
}
