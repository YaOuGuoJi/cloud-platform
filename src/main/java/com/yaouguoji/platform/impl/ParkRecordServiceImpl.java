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
        ParkRecordEntity parkRecordEntity=new ParkRecordEntity();
        BeanUtils.copyProperties(parkRecordDTO,parkRecordEntity);
        parkRecordEntity.setCarId(carFactoryByL(parkRecordDTO.getLicense()));
        return parkRecordMapper.addParkRecord(parkRecordEntity);
    }

    @Override
    public int updateParkRecordDTO(ParkRecordDTO parkRecordDTO) {
        ParkRecordEntity parkRecordEntity=new ParkRecordEntity();
        BeanUtils.copyProperties(parkRecordDTO,parkRecordEntity);
        parkRecordEntity.setCarId(carFactoryByL(parkRecordDTO.getLicense()));
        return parkRecordMapper.updateParkRecord(parkRecordEntity);
    }

    @Override
    public int deleteParkRecordDTO(int id) {
        return parkRecordMapper.deleteParkRecord(id);
    }

    @Override
    public ParkRecordDTO selectParkRecordDROById(int id) {
        ParkRecordEntity parkRecordEntity=parkRecordMapper.selectParkRecordById(id);
        String license=selectCarL(parkRecordEntity.getCarId());
        ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
        BeanUtils.copyProperties(parkRecordEntity,parkRecordDTO);
        parkRecordDTO.setLicense(license);
        return parkRecordDTO;
    }

    @Override
    public List<ParkRecordDTO> selectParkRecordDTOByL(String license) {
        CarEntity carEntitiy = carMapper.selectCarByL(license);
        List<ParkRecordEntity> parkRecordEntities=parkRecordMapper.selectParkRecordByCarId(carEntitiy.getId());
        List<ParkRecordDTO> parkRecordDTOS=new ArrayList<ParkRecordDTO>();
        if(parkRecordEntities!=null){
            for(Iterator<ParkRecordEntity> iterator=parkRecordEntities.iterator();iterator.hasNext();){
                ParkRecordEntity parkRecordEntity = (ParkRecordEntity) iterator.next();
                ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
                BeanUtils.copyProperties(parkRecordEntity,parkRecordDTO);
                parkRecordDTO.setLicense(license);
                parkRecordDTOS.add(parkRecordDTO);
            }
            return parkRecordDTOS;
        }
        return null;
    }

    @Override
    public List<ParkRecordDTO> selectParkRecordDTOA() {
        List<ParkRecordEntity> parkRecordEntities = parkRecordMapper.selectParkRecordAll();
        List<ParkRecordDTO> parkRecordDTOS=new ArrayList<ParkRecordDTO>();
        if(parkRecordEntities!=null){
            for(Iterator<ParkRecordEntity> iterator=parkRecordEntities.iterator();iterator.hasNext();){
                ParkRecordEntity parkRecordEntity = (ParkRecordEntity) iterator.next();
                ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
                BeanUtils.copyProperties(parkRecordEntity,parkRecordDTO);
                parkRecordDTO.setLicense(selectCarL(parkRecordEntity.getCarId()));
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

    /**
     * 新增停车记录时车，根据车牌查车
     * @param license
     * @return
     */
    public int carFactoryByL(String license){
        CarEntity carEntity = carMapper.selectCarByL(license);
        if (carEntity!=null) {
            return carEntity.getId();}
        else{ carEntity=new CarEntity();
        carEntity.setOwnerId(0);
        carEntity.setLicense(license);
        carMapper.addCar(carEntity);
        return carEntity.getId();}
    }

    /**
     * 查询停车记录根据车的id查车牌信息，找到返回，找不到返回“未登记”
     * @param id
     * @return
     */
    public String selectCarL(int id){
        CarEntity carEntity = carMapper.selectCarById(id);
        if(carEntity!=null){
            return carEntity.getLicense();
        }else{
            carEntity=new CarEntity();
            return "未登记";
        }
    }
}
