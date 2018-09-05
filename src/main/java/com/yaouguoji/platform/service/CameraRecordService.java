package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CameraRecordDTO;

import java.util.List;

public interface CameraRecordService {

    /**
     * 根据摄像头id列表查询最新记录
     *
     * @param cameraIds
     * @return
     */
    List<CameraRecordDTO> batchSelectAllRecords(List<Integer> cameraIds);

    /**
     * 根据id删除摄像头记录
     *
     * @param cRecordId
     * @return
     */
    void deleteByPrimaryKey(Integer cRecordId);

    /**
     * 添加摄像头记录
     *
     * @param record
     * @return
     */
    int insert(CameraRecordDTO record);

    /**
     * 根据id查询摄像头记录
     *
     * @param cRecordId
     * @return
     */
    CameraRecordDTO selectByPrimaryKey(Integer cRecordId);

    /**
     * 根据条件修改摄像头记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CameraRecordDTO record);
}
