package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserInfoDTO;
import com.yaouguoji.platform.vo.ObjectMapDTO;
import java.math.BigDecimal;
import java.util.List;

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
     * 新增用户信息
     *
     * @param userInfoDTO
     * @return
     */
    int addUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 更新用户信息
     *
     * @param userInfoDTO
     * @return
     */
    int updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 查询所有用户的全年消费金额并降序
     * @param year 年份
     * @return
     */
    List<ObjectMapDTO<Integer, BigDecimal>> findAllUserTotalConsumption(String year);

}
