package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CarEntity {
    /**
     * 汽车id
     */
    private Integer id;
    /**
     * 车主id
     */
    private Integer ownerId;
    /**
     * 车牌
     */
    private String license;
    /**
     * 新增时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
