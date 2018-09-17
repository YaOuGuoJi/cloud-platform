package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ParkRecordEntity {
    /**
     * 停车记录id
     */
    private Integer id;
    /**
     * 车辆id
     */
    private Integer carId;
    /**
     * 记录类型  0进1出
     */
    private Integer activeType;
    /**
     * 记录新增时间
     */
    private Date addTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
