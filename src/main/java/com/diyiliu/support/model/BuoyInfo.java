package com.diyiliu.support.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Description: BuoyInfo
 * Author: DIYILIU
 * Update: 2018-07-03 15:46
 */

@Data
public class BuoyInfo {

    private Long id;

    private String name;

    private String sim;

    private String type;

    private Integer gpsLocation;

    private Integer gpsSignal;

    private Double gpsLat;

    private Double gpsLng;

    private Double speed;

    private Double altitude;

    private Double temp;

    private Double voltage;

    private Date gpsTime;

    private Date sysTime;

    private Date createTime;

    public void setSim(String sim) {
        this.sim = sim;
        if (StringUtils.isEmpty(name)){
            this.name = sim;
        }
    }
}
