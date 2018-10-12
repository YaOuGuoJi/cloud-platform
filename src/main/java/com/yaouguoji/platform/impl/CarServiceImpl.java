package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.entity.CarEntity;
import com.yaouguoji.platform.mapper.CarMapper;
import com.yaouguoji.platform.service.CarService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Resource
    private CarMapper carMapper;

    @Override
    public int addCarDTO(CarDTO carDTO) {
        if (existLicense(carDTO.getLicense())) {
            return 0;
        }
        CarEntity carEntity = new CarEntity();
        BeanUtils.copyProperties(carDTO, carEntity);
        return carMapper.addCar(carEntity);
    }

    @Override
    public int updateCarDTO(CarDTO carDTO) {
        if (existLicense(carDTO.getLicense())) {
            return 0;
        }
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
        if (carEntity == null) {
            return null;
        }
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(carEntity, carDTO);
        return carDTO;
    }

    @Override
    public CarDTO selectCarDTOByLicense(String license) {
        CarEntity carEntity = carMapper.selectCarByLicense(license);
        if (carEntity == null) {
            return null;
        }
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(carEntity, carDTO);
        return carDTO;
    }

    @Override
    public List<CarDTO> selectAll() {
        List<CarEntity> carEntities = carMapper.selectAll();
        if (CollectionUtils.isEmpty(carEntities)) {
            return BeansListUtils.copyListProperties(carEntities, CarDTO.class);
        }
        return new ArrayList<>();
    }

    private boolean existLicense(String license) {
        return StringUtils.isEmpty(license) || carMapper.selectCarByLicense(license) != null;
    }
}