package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ParkRecordDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkRecordServiceTest {
    @Resource
    CarService carService;
    @Resource
    ParkRecordService parkRecordService;
    @Test
    public void testSelectCount(){
        System.out.println(parkRecordService.selectParkRecordDTOCout());
    }
    @Test
    public void addParkRecord(){
        ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
        parkRecordDTO.setActiveType(0);
        parkRecordDTO.setLicense("晋E2098");
        parkRecordService.addParkRecordDTO(parkRecordDTO);
    }
    @Test
    public  void updateParkRecord(){
        ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
        parkRecordDTO.setId(5);
        parkRecordDTO.setActiveType(0);
        parkRecordDTO.setLicense("晋E2049");
        parkRecordService.updateParkRecordDTO(parkRecordDTO);
    }
    @Test
    public void deleteParkRecordById(){
        parkRecordService.deleteParkRecordDTO(6);
    }
    @Test
    public void selectParkRecordI(){
        System.out.println(parkRecordService.selectParkRecordDROI(1));
    }

    @Test
    public void selectParkRecordA(){
        List<ParkRecordDTO> parkRecordDTOS = parkRecordService.selectParkRecordDTOA();
        System.out.println(parkRecordDTOS);
    }
}