package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderRecordDTO implements Serializable {

    private static final long serialVersionUID = 7732466140743098995L;
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商户id
     */
    private Integer shopId;

    /**
     * 下单途径
     */
    private String orderChannel;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 商品列表
     */
    private String productList;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 订单价格
     */
    private BigDecimal price;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
