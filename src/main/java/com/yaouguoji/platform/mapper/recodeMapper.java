package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.recode;

public interface recodeMapper {
    int deleteByPrimaryKey(Integer cRecodeId);

    int insert(recode record);

    int insertSelective(recode record);

    recode selectByPrimaryKey(Integer cRecodeId);

    int updateByPrimaryKeySelective(recode record);

    int updateByPrimaryKey(recode record);
}