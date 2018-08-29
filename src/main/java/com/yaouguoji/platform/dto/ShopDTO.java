package com.yaouguoji.platform.dto;

import lombok.Data;

@Data
public class ShopDTO {

    /**
     * 商户id
     */
    private Integer shopId;

    /**
     * 商户名
     */
    private String shopName;

    /**
     * 商户品牌
     */
    private Integer brandId;

    /**
     * 分区id
     */
    private Integer regionId;
}
