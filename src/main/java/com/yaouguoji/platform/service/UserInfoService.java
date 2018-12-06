package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserInfoDTO;

/**
 * @author liuwen
 */
public interface UserInfoService {

    /**
     * 查询用户详细信息
     *
     * @param userId
     * @return
     */
    UserInfoDTO findUserInfoByUserId(int userId);

    /**
     * 禁止使用该接口。如需添加用户请在区块链APP注册
     *
     * @param userInfoDTO
     * @return
     */
    @Deprecated
    int addUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 更新用户信息
     *
     * @param userInfoDTO
     * @return
     */
    int updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 查找所有用户总数
     * @return
     */
    int findTotalUserNum();

}
