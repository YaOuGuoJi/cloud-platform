package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CameraRecordDTO implements Serializable {

    private static final long serialVersionUID = -901451712356303583L;

    private Integer cRecordId;

    private Integer cameraId;

    private Date crAddTime;

    private Date crUpdateTime;

    private Integer crNumber;
}
