package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CarDTO;

import java.util.List;

public interface CarService {
    /**
     * 新增汽车
     *
     */
    int addCarDTO( CarDTO carDTO);

    /**
     * 根据id修改汽车
     *
     */
    int updateCarDTO(CarDTO carDTO);

    /**
     * 根据汽车id删除汽车
     *
     */
    int deleteCarDTO(int id);

    /**
     * 根据id查汽车
     *
     */
    CarDTO selectCarDTOI(int id);

    /**
     * 根据车牌查车
     *
     */
    CarDTO selectCarDTOL(String license);

    /**
     * 查出所有的车（需要改成分页形式的）
     */
    List<CarDTO> selectAll();
}
