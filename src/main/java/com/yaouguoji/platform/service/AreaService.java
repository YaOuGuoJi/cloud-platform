package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.mapper.areaMapper;
import com.yaouguoji.platform.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AreaService {

    List<AreaDTO> selectAll();

    /**
     * 根据分区id删除分区
     * @param areaId
     * @return
     */
    void deleteByPrimaryKey(@Param("areaId") Integer areaId);

    /**
     * 添加分区信息
     * @param record
     * @return
     */
    int insert(@Param("recode") AreaDTO record);

    /**
     * 选择性添加分区信息
     * @param record
     * @return
     */
    int insertSelective(@Param("recode") AreaDTO record);

    /**
     * 根据主键id查询分区信息
     * @param areaId
     * @return
     */
    AreaDTO selectByPrimaryKey(@Param("areaId") Integer areaId);

    /**
     * 选择性修改分区信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("recode") AreaDTO record);

    /**
     * 根据主键id修改分区信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("recode") AreaDTO record);
}
