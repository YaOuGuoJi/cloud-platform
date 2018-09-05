package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoEntity {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 性别 1-男 2-女 其他-未知
     */
    private int sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 职业/工作
     */
    private String job;

    /**
     * 汽车id
     */
    private int carId;

    /**
     * 是否VIP用户 1-是 0-不是
     */
    private int vip;

    /**
     * 身份证号码
     */
    private String identityId;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}