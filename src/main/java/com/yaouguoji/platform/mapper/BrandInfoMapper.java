package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.BrandInfoEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuwen
 */
public interface BrandInfoMapper {

    /**
     * 查
     *
     * @param brandId
     * @return
     */
    BrandInfoEntity findById(@Param("brandId") int brandId);

    /**
     * 增
     *
     * @param brandInfoEntity
     * @return
     */
    int addBrandInfo(@Param("brandInfoEntity") BrandInfoEntity brandInfoEntity);

    /**
     * 改
     *
     * @param brandInfoEntity
     * @return
     */
    int updateBrandInfo(@Param("brandInfoEntity") BrandInfoEntity brandInfoEntity);

    /**
     * 删除
     *
     * @param brandId
     * @return
     */
    int deleteBrandInfoById(@Param("brandId") int brandId);
}
