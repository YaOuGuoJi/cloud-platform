package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopInfoDTO implements Serializable {

    private static final long serialVersionUID = 3321702205850325312L;

    /**
     * 商户id
     */
    private Integer shopId;

    /**
     * 商户唯一标识
     */
    private String shopUuid;

    /**
     * 商户名称
     */
    private String shopName;

    /**
     * 商户分店名
     */
    private String branchName;

    /**
     * 区域id
     */
    private Integer regionId;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 商户类型
     */
    private String shopType;

    /**
     * 商户地址
     */
    private String address;

    /**
     * 商户logo
     */
    private String shopLogo;

    /**
     * 店主
     */
    private String shopOwner;

    /**
     * 联系电话
     */
    private String phoneNo;

    /**
     * 联系电话2
     */
    private String phoneNo2;

    /**
     * 人均价格
     */
    private BigDecimal avgPrice;

    /**
     * 删除标记
     */
    private int deleted;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
