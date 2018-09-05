package com.yaouguoji.platform.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CameraRecordDTO {

    private int cRecordId;

    private int cameraId;

    private Date crAddTime;

    private Date crUpdateTime;

    private int crNumber;
}
