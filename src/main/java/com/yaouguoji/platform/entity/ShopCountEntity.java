package com.yaouguoji.platform.entity;

import lombok.Data;

@Data
public class ShopCountEntity {

    /**
     * 下单方式
     */
    private Object orderChannel;
    /**
     * 订单数
     */
    private Object orderNumb;
    /**
     * 销售额
     */
    private Object amount;
    /**
     * 时间段
     */
    private Object Times;

}
