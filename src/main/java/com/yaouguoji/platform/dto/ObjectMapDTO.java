package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuwen
 */
@Data
public class ObjectMapDTO<O, N> implements Serializable {

    private static final long serialVersionUID = 1514838332075044027L;

    public ObjectMapDTO(O dtoObject, N number) {
        this.dtoObject = dtoObject;
        this.number = number;
    }

    public ObjectMapDTO(){

    }

    /**
     * dto对象
     */
    private O dtoObject;

    /**
     * 数据
     */
    private N number;
}
