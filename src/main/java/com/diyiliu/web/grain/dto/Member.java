package com.diyiliu.web.grain.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description: Member
 * Author: DIYILIU
 * Update: 2018-05-21 21:25
 */

@Data
@Entity
@Table(name = "grain_member")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String tel;

    private String address;

    private Date createTime;

    private String createUser;
}
