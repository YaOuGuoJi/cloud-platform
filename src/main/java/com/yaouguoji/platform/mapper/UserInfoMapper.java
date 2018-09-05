package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.dto.UserInfoDTO;
import com.yaouguoji.platform.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    /**
     * 查询
     * @param userId
     * @return
     */
    UserInfoEntity selectById(@Param("userId") int userId);

    /**
     * 更新
     * @param userInfoEntity
     * @return
     */
    int updateById(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 新增
     * @param userInfoEntity
     * @return
     */
    int insertUserInfo(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 身份判断（会员）
     * @param userId
     * @return
     */
    UserInfoEntity selectIsVip(@Param("userId") int userId);

}
