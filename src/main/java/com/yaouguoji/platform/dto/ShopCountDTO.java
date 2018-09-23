package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopCountDTO implements Serializable {
    private static final long serialVersionUID = 3165005138739178062L;
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
