package com.diyiliu.web.grain.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: Stock
 * Author: DIYILIU
 * Update: 2018-05-21 21:26
 */

@Data
@Entity
@Table(name = "grain_stock")
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    private Integer bookNo;

    private String serialNo;

    private Integer gross;

    private Integer tare;

    private Integer suttle;

    private Double price;

    private Double money;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Date createTime;

    private Date payTime;

    private Integer status;
}
