package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.CameraRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 摄像头记录
 */
public interface RecodeMapper {

    List<CameraRecord> selectAlls(@Param("cameraIdList") List<Integer> cameraIdList);

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
    int insert(@Param("record") CameraRecord record);

    /**
     * 根据条件添加摄像头记录
     * @param record
     * @return
     */
    int insertSelective(@Param("record") CameraRecord record);

    /**
     * 根据id查询摄像头记录
     * @param cRecordId
     * @return
     */
    CameraRecord selectByPrimaryKey(@Param("cRecordId") Integer cRecordId);

    /**
     * 根据条件修改摄像头记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("record") CameraRecord record);

    /**
     * 根据id查询摄像头记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("record") CameraRecord record);
}