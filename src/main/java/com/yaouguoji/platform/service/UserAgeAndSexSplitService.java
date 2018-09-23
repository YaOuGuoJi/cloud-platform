package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserAgeAndSexSplitDTO;

import java.util.Date;
import java.util.List;

public interface UserAgeAndSexSplitService {
    /**
     * 根据日期以及商铺的id查该商铺顾客的每个年龄段的男女人数以及性别该商铺总的男女人数
     *
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserAgeAndSexSplitDTO> selectAgeAndSexSplit(Integer shopId, Date startTime, Date endTime);
}
