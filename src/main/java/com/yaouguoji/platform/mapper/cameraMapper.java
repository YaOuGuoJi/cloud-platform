package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.area;
import com.yaouguoji.platform.entity.camera;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface cameraMapper {

    /**
     * 查询所有camera
     * @return
     */
    List<camera> selectAll();

    /**
     * 根据id删除摄像头信息
     * @param cameraId
     * @return
     */
    int deleteByPrimaryKey(@Param("cameraId") int cameraId);

    /**
     * 添加摄像头信息
     * @param record
     * @return
     */
    int insert(@Param("recode") camera record);

    /**
     * 根据条件添加摄像头信息
     * @param record
     * @return
     */
    int insertSelective(@Param("recode") camera record);

    /**
     * 根据id查询摄像头信息
     * @param cameraId
     * @return
     */
    camera selectByPrimaryKey(@Param("cameraId") int cameraId);

    /**
     * 选择性修改摄像头信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("recode") camera record);

    /**
     * 根据id修改摄像头信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("recode") camera record);
}