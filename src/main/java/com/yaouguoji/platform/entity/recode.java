package com.yaouguoji.platform.entity;

import java.util.Date;

public class recode {
    private Integer cRecodeId;

    private Integer cameraId;

    private Date time;

    public Integer getcRecodeId() {
        return cRecodeId;
    }

    public void setcRecodeId(Integer cRecodeId) {
        this.cRecodeId = cRecodeId;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}