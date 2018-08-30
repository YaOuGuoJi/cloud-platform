package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShopDTO implements Serializable {

    private static final long serialVersionUID = -829433840843387396L;
    /**
     * 商户id
     */
    private Integer id;

    /**
     * 商户名
     */
    private String shopName;

    /**
     * 商户品牌
     */
    private Integer brandId;

    /**
     * 分区id
     */
    private Integer regionId;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
