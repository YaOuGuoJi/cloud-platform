package com.yaouguoji.platform.entity;

import java.util.Date;

public class AreaEntity {
    private int areaId;

    private String aName;

    private String aSort;

    private Date aAddTime;

    private Date aUpdateTime;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName == null ? null : aName.trim();
    }

    public String getaSort() {
        return aSort;
    }

    public void setaSort(String aSort) {
        this.aSort = aSort == null ? null : aSort.trim();
    }

    public Date getaAddTime() {
        return aAddTime;
    }

    public void setaAddTime(Date aAddTime) {
        this.aAddTime = aAddTime;
    }

    public Date getaUpdateTime() {
        return aUpdateTime;
    }

    public void setaUpdateTime(Date aUpdateTime) {
        this.aUpdateTime = aUpdateTime;
    }
}