package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.entity.CarEntity;
import com.yaouguoji.platform.mapper.CarMapper;
import com.yaouguoji.platform.service.CarService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Resource
    private CarMapper carMapper;

    @Override
    public int addCarDTO(CarDTO carDTO) {
        if (carMapper.selectCarByLicense(carDTO.getLicense()) != null)
            return 0;
        CarEntity carEntity = new CarEntity();
        BeanUtils.copyProperties(carDTO, carEntity);
        return carMapper.addCar(carEntity);
    }

    @Override
    public int updateCarDTO(CarDTO carDTO) {
        if (carMapper.selectCarByLicense(carDTO.getLicense()) != null)
            return 0;
        CarEntity carEntity = new CarEntity();
        BeanUtils.copyProperties(carDTO, carEntity);
        return carMapper.updateCar(carEntity);
    }

    @Override
    public int deleteCarDTO(int id) {
        return carMapper.deleteCar(id);
    }

    @Override
    public CarDTO selectCarDTOById(int id) {
        CarEntity carEntity = carMapper.selectCarById(id);
        if (carEntity == null)
            return null;
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(carEntity, carDTO);
        return carDTO;
    }

    @Override
    public CarDTO selectCarDTOByLicense(String license) {
        CarEntity carEntity = carMapper.selectCarByLicense(license);
        if (carEntity == null)
            return null;
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(carEntity, carDTO);
        return carDTO;
    }

    @Override
    public List<CarDTO> selectAll() {
        List carEntities = carMapper.selectAll();
        if (carEntities != null) {
            List<CarDTO> carDTOs = new ArrayList<>();
            carDTOs = BeansListUtils.copyListProperties(carEntities, CarDTO.class);
            return carDTOs;
        }
        return null;
    }
}
