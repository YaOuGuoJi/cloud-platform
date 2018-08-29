package com.yaouguoji.platform.constant;

public enum HttpStatus {

    OK(200, "正常"),

    NOT_FOUND(404, "找不到页面");

    public int value;

    public String desc;

    HttpStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
