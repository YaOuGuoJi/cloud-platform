package com.yaouguoji.platform.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserFrequencyCountAndPriceCountEntity {
    private Integer userId;
    private Integer frequencyCount;
    private BigDecimal priceCount;
}
