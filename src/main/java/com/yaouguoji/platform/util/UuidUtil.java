package com.yaouguoji.platform.util;

import java.util.UUID;

public class UuidUtil {

    /**
     * 生成一个uuid
     * @return
     */
    public static String buildUuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
