package com.yaouguoji.platform.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRecordJsonEntity extends OrderRecordEntity{

    /**
     * 商户类型
     */
    private String shopType;

    /**
     * 商户分店名
     */
    private String branchName;

    /**
     * 商户名称
     */
    private String shopName;

}
