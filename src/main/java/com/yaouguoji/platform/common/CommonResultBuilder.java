package com.yaouguoji.platform.common;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.enums.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author liuwen
 * @date 2018/9/10
 */
public class CommonResultBuilder {

    private CommonResult<Map<String, Object>> commonResult = new CommonResult<>();

    private Map<String, Object> data = Maps.newHashMap();

    public CommonResultBuilder code(int code) {
        commonResult.setCode(code);
        if (HttpStatus.OK.value == code) {
            commonResult.setSuccess(true);
        }
        return this;
    }

    public CommonResultBuilder message(String message) {
        commonResult.setMessage(message);
        return this;
    }

    public CommonResultBuilder data(String name, Object object) {
        data.put(name, object);
        return this;
    }

    public CommonResult<Map<String, Object>> build() {
        if (!CollectionUtils.isEmpty(data)) {
            commonResult.setData(data);
        }
        return commonResult;
    }
}
