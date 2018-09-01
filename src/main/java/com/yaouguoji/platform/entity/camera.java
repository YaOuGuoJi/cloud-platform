package com.yaouguoji.platform.entity;

import java.util.Date;

public class camera {

    private int cameraId;

    private String cName;

    private int areaId;

    private Date cAddTime;

    private Date cUpdateTime;

    private String cIp;

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Date getcAddTime() {
        return cAddTime;
    }

    public void setcAddTime(Date cAddTime) {
        this.cAddTime = cAddTime;
    }

    public Date getcUpdateTime() {
        return cUpdateTime;
    }

    public void setcUpdateTime(Date cUpdateTime) {
        this.cUpdateTime = cUpdateTime;
    }

    public String getcIp() {
        return cIp;
    }

    public void setcIp(String cIp) {
        this.cIp = cIp;
    }
}