package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CarDTO;

import java.util.List;

public interface CarService {
    /**
     * 新增汽车
     *
     * @param carDTO
     * @return
     */
    int addCarDTO(CarDTO carDTO);

    /**
     * 修改汽车
     *
     * @param carDTO
     * @return
     */
    int updateCarDTO(CarDTO carDTO);

    /**
     * 根据id删除汽车
     *
     * @param id
     * @return
     */
    int deleteCarDTO(int id);

    /**
     * 根据id查汽车
     *
     * @param id
     * @return
     */
    CarDTO selectCarDTOById(int id);

    /**
     * 根据车牌查车
     *
     * @param license
     * @return
     */
    CarDTO selectCarDTOByLicense(String license);

    /**
     * 查出所有汽车
     *
     * @return
     */
    List<CarDTO> selectAll();
}
