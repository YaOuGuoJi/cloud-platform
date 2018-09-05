package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.AreaDTO;

import java.util.List;


public interface AreaService {

    /**
     *  查询全量区域信息
     *
     * @return
     */
    List<AreaDTO> selectAll();

    /**
     * 根据分区id删除分区
     *
     * @param areaId
     * @return
     */
    void deleteByPrimaryKey(Integer areaId);

    /**
     * 添加分区信息
     *
     * @param record
     * @return
     */
    int insert(AreaDTO record);

    /**
     * 根据主键id查询分区信息
     *
     * @param areaId
     * @return
     */
    AreaDTO selectByPrimaryKey(Integer areaId);

    /**
     * 选择性修改分区信息
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AreaDTO record);

}
