package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserInfoDTO;

public interface UserInfoService {

    /**
     * 查询用户详细信息
     * @param userId
     * @return
     */
    UserInfoDTO findUserInfoByUserId(int userId);

    /**
     * 新增用户信息
     * @param userInfoDTO
     * @return
     */
    int addUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 更新用户信息
     * @param userInfoDTO
     * @return
     */
    int updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 查询是否是VIP
     * @param userId
     * @return
     */
    boolean selectIsVip(int userId);

}
