package com.yaouguoji.platform.constant;

import lombok.Getter;

/**
 * @author Morse1113
 * @param <Content>
 */
public class CommonResult<Content> {

    /**
     * 返回码
     */
    @Getter
    private int code;

    /**
     * 是否成功
     */
    @Getter
    private boolean success;

    /**
     * 提示信息
     */
    @Getter
    private String message;

    /**
     * 返回数据
     */
    @Getter
    private Content data;

    public CommonResult() {

    }

    public static <Content> CommonResult<Content> success() {
        CommonResult<Content> result = new CommonResult<>();
        result.setCode(HttpStatus.OK.value);
        result.setSuccess(true);
        result.setMessage("");
        return result;
    }

    public static <Content> CommonResult<Content> success(Content data) {
        CommonResult<Content> result = success();
        result.setData(data);
        return result;
    }

    public static <Content> CommonResult<Content> fail(HttpStatus status) {
        return fail(status, "");
    }

    public static <Content> CommonResult<Content> fail(HttpStatus status, String message) {
        CommonResult<Content> result = new CommonResult<>();
        result.setCode(status.value);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public CommonResult<Content> setCode(int code) {
        this.code = code;
        return this;
    }

    public CommonResult<Content> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public CommonResult<Content> setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommonResult<Content> setData(Content data) {
        this.data = data;
        return this;
    }
}
