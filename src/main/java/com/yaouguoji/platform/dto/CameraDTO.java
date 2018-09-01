package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CameraDTO implements Serializable {

    private static final long serialVersionUID = -829433840843387396L;

    private Integer cameraId;

    private String cName;

    private Integer areaId;

    private Date cAddTime;

    private Date cUpdateTime;

    private String cIp;


}
