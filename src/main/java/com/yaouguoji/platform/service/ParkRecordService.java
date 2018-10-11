package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ParkRecordDTO;

import java.util.List;

public interface ParkRecordService {
    /**
     * 新增记录
     *
     * @param parkRecordDTO
     * @return
     */
    int addParkRecordDTO(ParkRecordDTO parkRecordDTO);

    /**
     * 删除记录
     *
     * @param id
     * @return
     */
    int deleteParkRecordDTO(int id);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    ParkRecordDTO selectParkRecordDROById(int id);

    /**
     * 根据车辆的车牌查询停车记录
     *
     * @param license
     * @return
     */
    List<ParkRecordDTO> selectParkRecordDTOByLicense(String license);

    /**
     * 查询停车场现在的车数
     *
     * @return
     */
    int selectNowCarNum();


}
