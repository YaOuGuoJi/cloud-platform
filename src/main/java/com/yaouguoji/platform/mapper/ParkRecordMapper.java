package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.ParkRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParkRecordMapper {
         /**
        * 新增停车记录
        *
        */
        int addParkRecord(@Param("parkRecord") ParkRecordEntity parkRecordEntity);
        /**
        * 根据停车记录id 修改停车记录
         */
         int updateParkRecord(@Param("parkRecord")ParkRecordEntity parkRecordEntity);
         /**
        * 根据停车记录id 删除停车记录
        */
        int deleteParkRecord(@Param("id")int id);
        /**
         * 根据停车记录id查询停车记录
         */
        ParkRecordEntity selectParkRecordById(@Param("id")int id);
        /**
         * 根据车id查询停车记录
         */
        List<ParkRecordEntity> selectParkRecordByCarId(@Param("cid")int carId);
        /**
        * 查出所有停车记录（需要改成分页形式的）
        */
        List<ParkRecordEntity> selectParkRecordAll();
        /**
        * 查出停车场现在停车数量
        */
        int selectCount();



}
