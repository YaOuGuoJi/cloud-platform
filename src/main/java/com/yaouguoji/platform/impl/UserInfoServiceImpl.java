package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.UserInfoDTO;
import com.yaouguoji.platform.entity.UserInfoEntity;
import com.yaouguoji.platform.mapper.UserInfoMapper;
import com.yaouguoji.platform.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfoDTO findUserInfoByUserId(int userId) {
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(userId);
        if (userInfoEntity == null) {
            return null;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoEntity, userInfoDTO);
        return userInfoDTO;
    }

    @Override
    public int addUserInfo(UserInfoDTO userInfoDTO) {
        if (userInfoDTO == null || StringUtils.isBlank(userInfoDTO.getUserName())) {
            return 0;
        }
        UserInfoEntity entity = new UserInfoEntity();
        userInfoDTO.setUserId(0);
        BeanUtils.copyProperties(userInfoDTO, entity);
        userInfoMapper.insertUserInfo(entity);
        return entity.getUserId();
    }

    @Override
    public int updateUserInfo(UserInfoDTO userInfoDTO) {
        if (userInfoDTO == null || userInfoDTO.getUserId() <= 0) {
            return 0;
        }
        UserInfoEntity entity = new UserInfoEntity();
        BeanUtils.copyProperties(userInfoDTO, entity);
        return userInfoMapper.updateById(entity);
    }
}
