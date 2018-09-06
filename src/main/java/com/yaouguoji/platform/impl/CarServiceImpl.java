package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.entity.CarEntity;
import com.yaouguoji.platform.mapper.CarMapper;
import com.yaouguoji.platform.service.CarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Resource
    private CarMapper carMapper;
    @Override
    public int addCarDTO(CarDTO carDTO) {
        CarEntity carEntity=new CarEntity();
        BeanUtils.copyProperties(carDTO,carEntity);
        return carMapper.addCar(carEntity);
    }

    @Override
    public int updateCarDTO(CarDTO carDTO) {
        CarEntity carEntity = new CarEntity();
        BeanUtils.copyProperties(carDTO,carEntity);
        return carMapper.updateCar(carEntity);
    }

    @Override
    public int deleteCarDTO(int id) {
        return carMapper.deleteCar(id);
    }

    @Override
    public CarDTO selectCarDTOI(int id) {
        CarEntity carEntity = carMapper.selectCarI(id);
        CarDTO carDTO=new CarDTO();
        BeanUtils.copyProperties(carEntity,carDTO);
        return carDTO;
    }

    @Override
    public CarDTO selectCarDTOL(String license) {
        CarDTO carDTO=new CarDTO();
        CarEntity carEntitie=carMapper.selectCarL(license);
        BeanUtils.copyProperties(carEntitie,carDTO);
            return carDTO;
    }

    @Override
    public List<CarDTO> selectAll() {
        List<CarDTO> carDTOS=new ArrayList<CarDTO>();
        List carEntities=carMapper.selectAll();
        if(carEntities!=null){
            for( Iterator iterator = carEntities.iterator();iterator.hasNext();){
                CarEntity carEntity = (CarEntity)iterator.next();
                CarDTO carDTO=new CarDTO();
                BeanUtils.copyProperties(carEntity,carDTO);
                carDTOS.add(carDTO);
            }
            return carDTOS;
        }
        return null;
    }
}
