package com.yaouguoji.platform.dto;

import java.util.Date;

public class ParkRecordDTO {
    /**
     * 停车记录id
     */
    private  Integer id;
    /**
     * 车牌
     */
    private String license;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    /**
     * 记录类型  0进1出
     */
    private Integer activeType;
    /**
     * 记录新增时间
     */
    private Date addTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActiveType() {
        return activeType;
    }

    public void setActiveType(Integer activeType) {
        this.activeType = activeType;
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
        return "ParkRecordDTO{" +
                "id=" + id +
                ", license='" + license + '\'' +
                ", activeType=" + activeType +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
