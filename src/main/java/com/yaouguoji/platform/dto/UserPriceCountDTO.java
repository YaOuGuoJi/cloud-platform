package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserPriceCountDTO implements Serializable {
    private static final long serialVersionUID = 3392179774135797211L;
    private BigDecimal totalPrice;
    private BigDecimal averagePrice;
    private Integer lessAverage;
    private Integer nearAverage;
    private Integer overAverage;

    public UserPriceCountDTO() {
    }

    public UserPriceCountDTO(BigDecimal totalPrice, BigDecimal averagePrice, Integer lessAverage, Integer nearAverage, Integer overAverage) {
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.lessAverage = lessAverage;
        this.nearAverage = nearAverage;
        this.overAverage = overAverage;
    }
}
