package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ParkRecordDTO implements Serializable {

    private static final long serialVersionUID = 1369490596127540477L;

    /**
     * 停车记录id
     */
    private Integer id;

    /**
     * 车辆id
     */
    private String license;

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