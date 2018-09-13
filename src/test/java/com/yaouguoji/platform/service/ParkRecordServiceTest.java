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
        parkRecordDTO.setActiveType(1);
        parkRecordDTO.setLicense("晋E2015");
        parkRecordService.addParkRecordDTO(parkRecordDTO);
    }
    @Test
    public  void testUpdateParkRecord(){
        ParkRecordDTO parkRecordDTO=new ParkRecordDTO();
        parkRecordDTO.setId(5);
        parkRecordDTO.setActiveType(0);
        parkRecordDTO.setLicense("晋E2093");
        parkRecordService.updateParkRecordDTO(parkRecordDTO);
    }
    @Test
    public void testDeleteParkRecord(){
        parkRecordService.deleteParkRecordDTO(5);
    }
    @Test
    public void testSelectParkRecordByI(){
        System.out.println(parkRecordService.selectParkRecordDROById(2));
    }

    @Test
    public void TestSelectParkRecordByL(){
        List<ParkRecordDTO> parkRecordDTOS = parkRecordService.selectParkRecordDTOByL("晋E2093");
        System.out.println(parkRecordDTOS);

    }

    @Test
    public void selectParkRecordA(){
        List<ParkRecordDTO> parkRecordDTOS = parkRecordService.selectParkRecordDTOA();
        System.out.println(parkRecordDTOS);
    }
}