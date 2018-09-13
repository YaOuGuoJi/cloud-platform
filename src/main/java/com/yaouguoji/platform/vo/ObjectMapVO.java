package com.yaouguoji.platform.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuwen
 */
@Data
public class ObjectMapVO implements Serializable {

    private static final long serialVersionUID = 1514838332075044027L;

    public ObjectMapVO(Object dtoObject, Object number) {
        this.dtoObject = dtoObject;
        this.number = number;
    }

    /**
     * dto对象
     */
    private Object dtoObject;

    /**
     * 数据
     */
    private Object number;
}
