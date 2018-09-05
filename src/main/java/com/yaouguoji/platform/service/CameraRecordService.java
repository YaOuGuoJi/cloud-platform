package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CameraRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CameraRecordService {

    List<CameraRecordDTO> selectAlls(List<Integer> cameraIds);

    /**
     * 根据id删除摄像头记录
     * @param cRecordId
     * @return
     */
    void deleteByPrimaryKey(Integer cRecordId);

    /**
     * 添加摄像头记录
     * @param record
     * @return
     */
    int insert(CameraRecordDTO record);

    /**
     * 根据条件添加摄像头记录
     * @param record
     * @return
     */
    int insertSelective(CameraRecordDTO record);

    /**
     * 根据id查询摄像头记录
     * @param cRecordId
     * @return
     */
    CameraRecordDTO selectByPrimaryKey(Integer cRecordId);

    /**
     * 根据条件修改摄像头记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelectives(CameraRecordDTO record);

    /**
     * 根据id查询摄像头记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(CameraRecordDTO record);
}
