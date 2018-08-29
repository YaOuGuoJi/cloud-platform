package com.yaouguoji.platform.entity;

import lombok.Data;

@Data
public class ShopEntity {

    /**
     * 商户id
     */
    private int shopId;

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
}
