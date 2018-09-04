package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.recode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface recodeMapper {

    List<recode> selectAll(@Param("cameraIdList") List<Integer> cameraIdList);

    List<recode> selectAlls(@Param("cameraIdList") List<Integer> cameraIdList);

    /**
     * 根据id删除摄像头记录
     * @param cRecordId
     * @return
     */
    int deleteByPrimaryKey(@Param("cRecordId") Integer cRecordId);

    /**
     * 添加摄像头记录
     * @param record
     * @return
     */
    int insert(@Param("record") recode record);

    /**
     * 根据条件添加摄像头记录
     * @param record
     * @return
     */
    int insertSelective(@Param("record") recode record);

    /**
     * 根据id查询摄像头记录
     * @param cRecordId
     * @return
     */
    recode selectByPrimaryKey(@Param("cRecordId") Integer cRecordId);

    /**
     * 根据条件修改摄像头记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("record") recode record);

    /**
     * 根据id查询摄像头记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("record") recode record);
}