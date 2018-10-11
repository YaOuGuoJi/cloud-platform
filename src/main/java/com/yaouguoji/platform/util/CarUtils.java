package com.yaouguoji.platform.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liuwen
 * @date 2018/9/20
 */
public class CarUtils {

    public static boolean checkLicense(String license) {
        return StringUtils.isNotEmpty(license) && (license.length() == 7 || license.length() == 8);
    }
}
