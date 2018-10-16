package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
public class SalesDTO implements Serializable {
    private static final long serialVersionUID = -1296704492879147180L;
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
}
