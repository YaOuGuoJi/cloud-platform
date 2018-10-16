package com.yaouguoji.platform.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopCountEntity {

    /**
     * 下单方式
     */
    private String orderChannel;
    /**
     * 订单数
     */
    private int orderNumb;
    /**
     * 销售额
     */

    private BigDecimal amount;
    /**
     * 时间段
     */
    private String Time;
    /**
     * 商品类别
     */
    private String productType;
}
