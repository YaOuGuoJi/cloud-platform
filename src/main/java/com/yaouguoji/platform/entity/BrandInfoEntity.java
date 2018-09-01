package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BrandInfoEntity {

    /**
     * 品牌id
     */
    private int brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌logo
     */
    private String brandLogo;

    /**
     * 删除标记 1-已删除 0-正常
     */
    private int deleted;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
