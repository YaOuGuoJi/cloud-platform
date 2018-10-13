package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
public class GoodsSellTypeDTO implements Serializable {
    private static final long serialVersionUID = 178705633869795972L;
    /**
     * 订单数
     */
    private int orderNumb;
    /**
     * 销售额
     */
    private BigDecimal amount;
    /**
     * 商品类别
     */
    private String productType;
}
