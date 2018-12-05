package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/12/4
 */
@Data
public class ShopAccountDTO implements Serializable {

    private static final long serialVersionUID = 7331828355432058031L;
    /**
     * 商户id
     */
    private Integer shopId;

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
