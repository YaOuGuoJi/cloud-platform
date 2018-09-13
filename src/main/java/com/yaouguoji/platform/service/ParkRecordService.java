package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ParkRecordDTO;
import com.yaouguoji.platform.mapper.CarMapper;

import java.util.List;

public interface ParkRecordService {
    /**
     * 新增记录
     * @param parkRecordDTO
     * @return
     */
    int addParkRecordDTO(ParkRecordDTO parkRecordDTO);

    /**
     * 修改记录
     * @param parkRecordDTO
     * @return
     */
    int updateParkRecordDTO(ParkRecordDTO parkRecordDTO);

    /**
     * 删除记录
     * @param id
     * @return
     */
    int deleteParkRecordDTO(int id);

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    ParkRecordDTO selectParkRecordDROById(int id);

    /**
     * 根据车辆的车牌查询停车记录
     * @param license
     * @return
     */
    List<ParkRecordDTO>selectParkRecordDTOByL(String license);

    /**
     * 查询所有停车记录(后期改为分页的)
     * @return
     */
    List<ParkRecordDTO>selectParkRecordDTOA();

    /**
     * 查询停车场现在的车数
     * @return
     */
    int selectParkRecordDTOCout();

}
