package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserInfoEntity;
import com.yaouguoji.platform.vo.ObjectMapDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

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
    int insertUserInfo(@Param("userInfoEntity") UserInfoEntity userInfoEntity);

    /**
     * 查找所有用户总数
     * @return
     */
    int findTotalUserNum();


}
