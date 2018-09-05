package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.AreaEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分区
 */

public interface AreaMapper {

    /**
     * 查询所有area
     * @return
     */
    List<AreaEntity> selectAll();

    /**
     * 根据分区id删除分区
     * @param areaId
     * @return
     */
    int deleteByPrimaryKey(@Param("areaId") int areaId);

    /**
     * 添加分类信息
     * @param recode
     * @return
     */
    int insert(@Param("recode")AreaEntity recode);

    /**
     * 选择性添加分区信息
     * @param record
     * @return
     */
    int insertSelective(@Param("recode") AreaEntity record);

    /**
     * 根据主键id查询分区信息
     * @param areaId
     * @return
     */
    AreaEntity selectByPrimaryKey(@Param("areaId") int areaId);

    /**
     * 修改分区信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("recode") AreaEntity record);

    /**
     * 根据主键id修改分区信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("recode") AreaEntity record);
}