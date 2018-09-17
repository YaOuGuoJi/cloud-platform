package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ParkRecordDTO;
import com.yaouguoji.platform.entity.CarEntity;
import com.yaouguoji.platform.entity.ParkRecordEntity;
import com.yaouguoji.platform.mapper.CarMapper;
import com.yaouguoji.platform.mapper.ParkRecordMapper;
import com.yaouguoji.platform.service.ParkRecordService;
import com.yaouguoji.platform.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkRecordServiceImpl implements ParkRecordService {
    @Resource
    private ParkRecordMapper parkRecordMapper;
    @Resource
    private CarMapper carMapper;

    @Override
    public int addParkRecordDTO(ParkRecordDTO parkRecordDTO) {
        ParkRecordEntity parkRecordEntity = new ParkRecordEntity();
        BeanUtils.copyProperties(parkRecordDTO, parkRecordEntity);
        parkRecordEntity.setCarId(carFactoryByLicense(parkRecordDTO.getLicense()));
        return parkRecordMapper.addParkRecord(parkRecordEntity);
    }

    @Override
    public int deleteParkRecordDTO(int id) {
        return parkRecordMapper.deleteParkRecord(id);
    }

    @Override
    public ParkRecordDTO selectParkRecordDROById(int id) {
        ParkRecordEntity parkRecordEntity = parkRecordMapper.selectParkRecordById(id);
        if (parkRecordEntity != null) {
            ParkRecordDTO parkRecordDTO = new ParkRecordDTO();
            BeanUtils.copyProperties(parkRecordEntity, parkRecordDTO);
            parkRecordDTO.setLicense(carMapper.selectCarById(parkRecordEntity.getCarId()).getLicense());
            return parkRecordDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<ParkRecordDTO> selectParkRecordDTOByLicense(String license) {
        CarEntity carEntity = carMapper.selectCarByLicense(license);
        if (carEntity != null) {
            List<ParkRecordEntity> parkRecordEntities = parkRecordMapper.selectParkRecordByCarId(carEntity.getId());
            List<ParkRecordDTO> parkRecordDTOs = BeansListUtils.copyListProperties(parkRecordEntities, ParkRecordDTO.class);
            parkRecordDTOs.forEach(parkRecordDTO -> parkRecordDTO.setLicense(license));
            return parkRecordDTOs;
        }
        return null;
    }

    @Override
    public List<ParkRecordDTO> selectParkRecordDTOAll() {
        List<ParkRecordEntity> parkRecordEntities = parkRecordMapper.selectParkRecordAll();
        List<ParkRecordDTO> parkRecordDTOs = new ArrayList<>();
        if (parkRecordEntities != null) {
            for (ParkRecordEntity parkRecordEntity : parkRecordEntities) {
                ParkRecordDTO parkRecordDTO = new ParkRecordDTO();
                BeanUtils.copyProperties(parkRecordEntity, parkRecordDTO);
                parkRecordDTO.setLicense(carMapper.selectCarById(parkRecordEntity.getCarId()).getLicense());
                parkRecordDTOs.add(parkRecordDTO);
            }
            return parkRecordDTOs;
        }
        return null;
    }

    @Override
    public int selectNowCarNum() {
        List<Integer> integers = parkRecordMapper.selectNowCarNum();
        int count[] = new int[2];
        int i = 0;
        for (Integer integer : integers) {
            count[i] = integer;
            i++;
        }
        return count[0] > count[1] ? count[0] - count[1] : count[1] - count[0];
    }

    /**
     * 新增停车记录时车，根据车牌查车,若无车则增车
     *
     * @param license
     * @return
     */
    private int carFactoryByLicense(String license) {
        CarEntity carEntity = carMapper.selectCarByLicense(license);
        if (carEntity != null) {
            return carEntity.getId();
        }
        carEntity = new CarEntity();
        carEntity.setOwnerId(0);
        carEntity.setLicense(license);
        carMapper.addCar(carEntity);
        return carEntity.getId();

    }
}
