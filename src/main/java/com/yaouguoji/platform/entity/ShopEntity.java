package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ShopEntity {

    /**
     * 商户id
     */
    private int id;

    /**
     * 商户名
     */
    private String shopName;

    /**
     * 商户品牌
     */
    private int brandId;

    /**
     * 分区id
     */
    private int regionId;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
