package com.yaouguoji.platform.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeansListUtils {

    /**
     * 将sourceList 拷贝为T类型的List
     * @param sourceList
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyListProperties(List<?> sourceList, Class<T> tClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> targets = new ArrayList<>();
        sourceList.forEach(source -> {
            try {
                T target = tClass.newInstance();
                BeanUtils.copyProperties(source, target);
                targets.add(target);
            } catch (Exception e) {
                throw new FatalBeanException("Copy bean [" + source.toString() + "] failed.", e);
            }
        });
        return targets;
    }
}
