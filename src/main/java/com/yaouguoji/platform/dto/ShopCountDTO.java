package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopCountDTO implements Serializable {
    private static final long serialVersionUID = -1296704492879147180L;

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
