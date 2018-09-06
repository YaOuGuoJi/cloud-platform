package com.yaouguoji.platform.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopInfoEntity {

    /**
     * 商户id
     */
    private int shopId;

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
    private int regionId;

    /**
     * 品牌id
     */
    private int brandId;

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
