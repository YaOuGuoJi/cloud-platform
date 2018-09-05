package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AreaDTO implements Serializable {

    private static final long serialVersionUID = -829433840843387396L;

    /**
     * 分区主键id
     */
    private Integer areaId;

    /**
     * 分区名称
     */
    private String aName;

    /**
     * 分区
     */
    private String aSort;

    /**
     * 添加时间
     */
    private Date aAddTime;

    /**
     * 更改时间
     */
    private Date aUpdateTime;

    private int number;


}
