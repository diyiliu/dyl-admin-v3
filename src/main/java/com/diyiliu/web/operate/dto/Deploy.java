package com.diyiliu.web.operate.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description: Deploy
 * Author: DIYILIU
 * Update: 2018-05-26 11:28
 */

@Data
@Entity
@Table(name = "opt_deploy")
public class Deploy {

    @Id
    @GeneratedValue
    private Long id;

    private String os;

    private String address;

    private String platform;

    private String tag;

    private String username;

    private String password;

    private String comment;

    private String createUser;

    private Date createTime;
}
