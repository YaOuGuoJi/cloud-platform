package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserPriceCountDTO implements Serializable {
    private static final long serialVersionUID = 3392179774135797211L;
    private BigDecimal totalPrice;
    private BigDecimal averagePrice;
    private Distributed distributed;

    public UserPriceCountDTO() {
        this.totalPrice = new BigDecimal("0.00");
        this.averagePrice = new BigDecimal("0.00");
        this.distributed = new Distributed();
    }

    @Data
    public class Distributed {
        private Integer lessAverage;
        private Integer nearAverage;
        private Integer overAverage;
    }
}
