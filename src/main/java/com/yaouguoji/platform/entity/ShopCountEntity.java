package com.yaouguoji.platform.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopCountEntity {

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 订单数
     */
    private Integer orderNumb;

    /**
     * 销售额
     */
    private BigDecimal amount;

}
