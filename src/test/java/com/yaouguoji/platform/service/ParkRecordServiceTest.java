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
    public void testNowCarNum() {
        System.out.println(parkRecordService.selectNowCarNum());
    }

    @Test
    public void addParkRecord() {
        ParkRecordDTO parkRecordDTO = new ParkRecordDTO();
        parkRecordDTO.setActiveType(1);
        parkRecordDTO.setLicense("晋E2016");
        parkRecordService.addParkRecordDTO(parkRecordDTO);
    }

    @Test
    public void testDeleteParkRecord() {
        parkRecordService.deleteParkRecordDTO(1);
    }

    @Test
    public void testSelectParkRecordById() {
        System.out.println(parkRecordService.selectParkRecordDROById(1));
    }

    @Test
    public void testSelectParkRecordByLicense() {
        List<ParkRecordDTO> parkRecordDTOs = parkRecordService.selectParkRecordDTOByLicense("晋E2011a");
        if (parkRecordDTOs != null)
            for (ParkRecordDTO parkRecordDTO : parkRecordDTOs)
                System.out.println(parkRecordDTO);

    }

    @Test
    public void selectParkRecordAll() {
        List<ParkRecordDTO> parkRecordDTOs = parkRecordService.selectParkRecordDTOAll();
        if (parkRecordDTOs != null)
            for (ParkRecordDTO parkRecordDTO : parkRecordDTOs)
                System.out.println(parkRecordDTO);
    }
}
