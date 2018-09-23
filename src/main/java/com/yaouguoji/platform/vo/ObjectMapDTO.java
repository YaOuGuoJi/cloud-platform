package com.yaouguoji.platform.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjectMapDTO<K, V> implements Serializable {

    private static final long serialVersionUID = 934136470905279916L;

    public ObjectMapDTO(K objectKey, V objectValue) {
        this.objectKey = objectKey;
        this.objectValue = objectValue;
    }

    private K objectKey;

    private V objectValue;

}
