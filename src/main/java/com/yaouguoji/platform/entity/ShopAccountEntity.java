package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/4
 */
@Data
public class ShopAccountEntity {

    /**
     * 商户id
     */
    private Integer shopId;

    /**
     * 密码
     */
    private String password;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
