package com.yaouguoji.platform.entity;

import java.util.Date;

public class CameraRecordEntity {
    private Integer cRecordId;

    private Integer cameraId;

    private Date crAddTime;

    private Date crUpdateTime;

    private Integer crNumber;

    public Integer getcRecordId() {
        return cRecordId;
    }

    public void setcRecordId(Integer cRecordId) {
        this.cRecordId = cRecordId;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Date getCrAddTime() {
        return crAddTime;
    }

    public void setCrAddTime(Date crAddTime) {
        this.crAddTime = crAddTime;
    }

    public Date getCrUpdateTime() {
        return crUpdateTime;
    }

    public void setCrUpdateTime(Date crUpdateTime) {
        this.crUpdateTime = crUpdateTime;
    }

    public int getCrNumber() {
        return crNumber;
    }

    public void setCrNumber(int crNumber) {
        this.crNumber = crNumber;
    }
}