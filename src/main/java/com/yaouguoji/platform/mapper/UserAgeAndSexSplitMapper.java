package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserAgeAndSexSplitEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserAgeAndSexSplitMapper {
    /**
     * 根据日期以及商铺的id查该商铺顾客的年龄以及性别分布
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserAgeAndSexSplitEntity> selectAgeAndSexSplitEntityMapper(@Param("shopId") Integer shopId,
                                                                    @Param("startTime") Date startTime,
                                                                    @Param("endTime") Date endTime);
}
