package com.yaouguoji.platform.dto;

import java.util.Date;

public class CarDTO {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", license='" + license + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
