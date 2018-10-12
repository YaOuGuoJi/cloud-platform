package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CarDTO implements Serializable {

    private static final long serialVersionUID = -9028071992340174085L;

    /**
     * 汽车id
     */
    private Integer id;

    /**
     * 车主id
     */
    private Integer ownerId;

    /**
     * 车牌
     */
    private String license;

    /**
     * 新增时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
