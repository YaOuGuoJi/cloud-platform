package com.yaouguoji.platform.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserPriceCountEntity {
    private Integer lessAverage;
    private Integer nearAverage;
    private Integer overAverage;
}
