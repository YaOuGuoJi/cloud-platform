package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.ParkRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParkRecordMapper {
    /**
     * 新增停车记录
     *
     * @param parkRecordEntity
     * @return
     */
    int addParkRecord(@Param("parkRecord") ParkRecordEntity parkRecordEntity);

    /**
     * 根据停车记录id 删除停车记录
     *
     * @param id
     * @return
     */
    int deleteParkRecord(@Param("id") int id);

    /**
     * 根据停车记录id查询停车记录
     *
     * @param id
     * @return
     */
    ParkRecordEntity selectParkRecordById(@Param("id") int id);

    /**
     * 根据车id查询停车记录
     *
     * @param carId
     * @return
     */
    List<ParkRecordEntity> selectParkRecordByCarId(@Param("cid") int carId);

    /**
     * 查出停车场现在停车数量
     *
     * @return
     */
    List<Integer> selectNowCarNum();
}
