package com.yaouguoji.platform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AreaEntity {
    private int areaId;

    private String aName;

    private String aSort;

    private Date aAddTime;

    private Date aUpdateTime;

    private String aAddress;

}