package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.UserAgeAndSexSplitDTO;
import com.yaouguoji.platform.entity.UserAgeAndSexSplitEntity;
import com.yaouguoji.platform.mapper.UserAgeAndSexSplitMapper;
import com.yaouguoji.platform.service.UserAgeAndSexSplitService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserAgeAndSexSplitServiceImpl implements UserAgeAndSexSplitService {
    @Resource
    private UserAgeAndSexSplitMapper userAgeAndSexSplitMapper;

    public List<UserAgeAndSexSplitDTO> selectAgeAndSexSplit(Integer shopId, Date startTime, Date endTime) {
        List<UserAgeAndSexSplitEntity> userAgeAndSexSplitEntities = userAgeAndSexSplitMapper.selectAgeAndSexSplitEntityMapper(shopId, startTime, endTime);
        if (userAgeAndSexSplitEntities != null) {
            List<UserAgeAndSexSplitDTO> userAgeAndSexSplitDTOs = BeansListUtils.copyListProperties(userAgeAndSexSplitEntities, UserAgeAndSexSplitDTO.class);
            return userAgeAndSexSplitDTOs;
        }
        return new ArrayList<UserAgeAndSexSplitDTO>();
    }
}
