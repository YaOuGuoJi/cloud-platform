package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.AreaDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AreaService {

    List<AreaDTO> selectAll();

    /**
     * 根据分区id删除分区
     * @param areaId
     * @return
     */
    void deleteByPrimaryKey(Integer areaId);

    /**
     * 添加分区信息
     * @param record
     * @return
     */
    int insert(AreaDTO record);

    /**
     * 选择性添加分区信息
     * @param record
     * @return
     */
    int insertSelective(AreaDTO record);

    /**
     * 根据主键id查询分区信息
     * @param areaId
     * @return
     */
    AreaDTO selectByPrimaryKey(Integer areaId);

    /**
     * 选择性修改分区信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AreaDTO record);

    /**
     * 根据主键id修改分区信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(AreaDTO record);
}
