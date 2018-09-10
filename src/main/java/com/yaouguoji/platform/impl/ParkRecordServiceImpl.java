package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.ParkRecordDTO;
import com.yaouguoji.platform.entity.CarEntity;
import com.yaouguoji.platform.entity.ParkRecordEntity;
import com.yaouguoji.platform.mapper.CarMapper;
import com.yaouguoji.platform.mapper.ParkRecordMapper;
import com.yaouguoji.platform.service.ParkRecordService;
import org.apache.ibatis.javassist.bytecode.LineNumberAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class ParkRecordServiceImpl implements ParkRecordService {
    @Resource
    private ParkRecordMapper parkRecordMapper;
    @Resource
    private CarMapper carMapper;
    @Override
    public int addParkRecordDTO(ParkRecordDTO parkRecordDTO) {
        CarEntity carEntity = carMapper.selectCarL(parkRecordDTO.getLicense());
        Integer id=0;
        if(carEntity==null){
            carEntity=new CarEntity();
            carEntity.setLicense(parkRecordDTO.getLicense());
            carEntity.setOwnerId(0);
            carMapper.addCar(carEntity);
            id=carEntity.getId();
        }
        else id=carEntity.getId();
        ParkRecordEntity parkRecordEntity=new ParkRecordEntity();
        BeanUtils.copyProperties(parkRecordDTO,parkRecordEntity);
        parkRecordEntity.setCarId(id);
        return parkRecordMapper.addParkRecord(parkRecordEntity);
    }

    @Override
    public int updateParkRecordDTO(ParkRecordDTO parkRecordDTO) {
        CarEntity carEntity = carMapper.selectCarL(parkRecordDTO.getLicense());
        Integer id=0;
        if(carEntity==null){
            carEntity=new CarEntity();
            carEntity.setLicense(parkRecordDTO.getLicense());
            carEntity.setOwnerId(0);
            carMapper.addCar(carEntity);
            id=carEntity.getId();
        }
        else id=carEntity.getId();
        ParkRecordEntity parkRecordEntity=new ParkRecordEntity();
        BeanUtils.copyProperties(parkRecordDTO,parkRecordEntity);
        parkRecordEntity.setCarId(id);
        return parkRecordMapper.updateParkRecord(parkRecordEntity);
    }

    @Override
    public int deleteParkRecordDTO(int id) {
        return parkRecordMapper.deleteParkRecord(id);
    }

    @Override
    public ParkRecordDTO selectParkRecordDROI(int id) {
        ParkRecordEntity parkRecordEntity=parkRecordMapper.selectParkRecordI(id);
        CarEntity carEntity = carMapper.selectCarI(parkRecordEntity.getCarId());
        ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
        BeanUtils.copyProperties(parkRecordEntity,parkRecordDTO);
        parkRecordDTO.setLicense(carEntity.getLicense());
        return parkRecordDTO;
    }

    @Override
    public List<ParkRecordDTO> selectParkRecordDTOL(String license) {
        CarEntity carEntitiy = carMapper.selectCarL(license);
        List<ParkRecordEntity> parkRecordEntitieslist=parkRecordMapper.selectParkRecordC(carEntitiy.getId());
        List<ParkRecordDTO> parkRecordDTOS=new ArrayList<ParkRecordDTO>();
        if(parkRecordEntitieslist!=null){
            for(Iterator<ParkRecordEntity> iterator=parkRecordEntitieslist.iterator();iterator.hasNext();){
                ParkRecordEntity parkRecordEntity = (ParkRecordEntity) iterator.next();
                ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
                BeanUtils.copyProperties(parkRecordEntity,parkRecordDTO);
                parkRecordDTO.setLicense(carEntitiy.getLicense());
                parkRecordDTOS.add(parkRecordDTO);
            }
            return parkRecordDTOS;
        }
        return null;
    }

    @Override
    public List<ParkRecordDTO> selectParkRecordDTOA() {
        List<ParkRecordEntity> parkRecordEntities = parkRecordMapper.selectParkRecordA();
        List<ParkRecordDTO> parkRecordDTOS=new ArrayList<ParkRecordDTO>();
        if(parkRecordEntities!=null){
            for(Iterator<ParkRecordEntity> iterator=parkRecordEntities.iterator();iterator.hasNext();){
                ParkRecordEntity parkRecordEntity = (ParkRecordEntity) iterator.next();
                CarEntity carEntity = carMapper.selectCarI(parkRecordEntity.getCarId());
                ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
                BeanUtils.copyProperties(parkRecordEntity,parkRecordDTO);
                parkRecordDTO.setLicense(carEntity.getLicense());
                parkRecordDTOS.add(parkRecordDTO);
            }
            return  parkRecordDTOS;
        }
        return null;
    }

    @Override
    public int selectParkRecordDTOCout() {
        return parkRecordMapper.selectCount();
    }
}
