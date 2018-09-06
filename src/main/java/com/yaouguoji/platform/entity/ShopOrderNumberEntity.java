package com.yaouguoji.platform.entity;

import lombok.Data;

@Data
public class ShopOrderNumberEntity {

    /**
     * 商户id
     */
    private int shopId;

    /**
     * 结果值
     */
    private Object result;
}
