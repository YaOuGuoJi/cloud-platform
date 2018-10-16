package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
public class SalesAndPlayOrderDTO implements Serializable {
    private static final long serialVersionUID = 6249949202327915508L;
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
    private Object Times;
}
