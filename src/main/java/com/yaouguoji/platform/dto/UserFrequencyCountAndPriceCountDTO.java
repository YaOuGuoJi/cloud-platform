package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFrequencyCountAndPriceCountDTO implements Serializable {
    private static final long serialVersionUID = 3392179774135797211L;
    private Integer count0_5Frequency;
    private Integer count5_10Frequency;
    private Integer countOver10Frequency;
    private Integer countlessAveragePrice;
    private Integer countOverAveragePrice;
    private Integer countFarAboveAveragePrice;

    public UserFrequencyCountAndPriceCountDTO() {
    }

    public UserFrequencyCountAndPriceCountDTO(Integer count0_5Frequency, Integer count5_10Frequency, Integer countOver10Frequency, Integer countlessAveragePrice, Integer countOverAveragePrice, Integer countFarAboveAveragePrice) {
        this.count0_5Frequency = count0_5Frequency;
        this.count5_10Frequency = count5_10Frequency;
        this.countOver10Frequency = countOver10Frequency;
        this.countlessAveragePrice = countlessAveragePrice;
        this.countOverAveragePrice = countOverAveragePrice;
        this.countFarAboveAveragePrice = countFarAboveAveragePrice;
    }
}
