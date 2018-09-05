package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.CameraEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 摄像头
 */
public interface CameraMapper {

    /**
     * 查询所有camera
     *
     * @return
     */
    List<CameraEntity> selectAll();

    /**
     * 根据id删除摄像头信息
     *
     * @param cameraId
     * @return
     */
    int deleteByPrimaryKey(@Param("cameraId") int cameraId);

    /**
     * 添加摄像头信息
     *
     * @param record
     * @return
     */
    int insert(@Param("recode") CameraEntity record);

    /**
     * 根据条件添加摄像头信息
     *
     * @param record
     * @return
     */
    int insertSelective(@Param("recode") CameraEntity record);

    /**
     * 根据id查询摄像头信息
     *
     * @param cameraId
     * @return
     */
    CameraEntity selectByPrimaryKey(@Param("cameraId") int cameraId);

    /**
     * 选择性修改摄像头信息
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("recode") CameraEntity record);

    /**
     * 根据id修改摄像头信息
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("recode") CameraEntity record);
}