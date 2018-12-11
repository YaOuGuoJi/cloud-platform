package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuwen
 */
public interface UserInfoMapper {

    /**
     * 查询
     *
     * @param userId
     * @return
     */
    UserInfoEntity selectById(@Param("userId") int userId);

    /**
     * 根据手机号查询用户信息
     *
     * @param phoneNum
     * @return
     */
    UserInfoEntity findUserInfoByPhoneNum(@Param("phoneNum") String phoneNum);

    /**
     * 更新
     *
     * @param userInfoEntity
     * @return
     */
    int updateById(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 新增
     *
     * @param userInfoEntity
     * @return
     */
    @Deprecated
    int insertUserInfo(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 查找所有用户总数
     * @return
     */
    int findTotalUserNum();


}
