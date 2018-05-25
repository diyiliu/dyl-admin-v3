package com.diyiliu.web.grain.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description: Sold
 * Author: DIYILIU
 * Update: 2018-05-25 22:25
 */

@Data
@Entity
@Table(name = "grain_sold")
public class Sold {

    @Id
    @GeneratedValue
    private Long id;

    private String company;

    private String serialNo;

    private Integer gross;

    private Integer tare;

    private Integer suttle;

    private Double price;

    private Double money;

    private Date createTime;
}
