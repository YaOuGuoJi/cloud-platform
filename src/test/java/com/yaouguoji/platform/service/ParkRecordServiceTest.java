package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.ParkRecordDTO;
import org.junit.Assert;
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
    private CarService carService;
    @Resource
    private ParkRecordService parkRecordService;

    @Test
    public void testNowCarNum() {
        Assert.assertNotNull(parkRecordService.selectNowCarNum());
    }

    @Test
    public void addParkRecord() {
        ParkRecordDTO parkRecordDTO = new ParkRecordDTO();
        parkRecordDTO.setActiveType(1);
        parkRecordDTO.setLicense("晋E2016");
        Assert.assertEquals(1,parkRecordService.addParkRecordDTO(parkRecordDTO));
    }

    @Test
    public void testDeleteParkRecord() {
        Assert.assertEquals(1,parkRecordService.deleteParkRecordDTO(2));
    }

    @Test
    public void testSelectParkRecordById() {
        Assert.assertNotNull(parkRecordService.selectParkRecordDROById(2));
    }

    @Test
    public void testSelectParkRecordByLicense() {
        List<ParkRecordDTO> parkRecordDTOs = parkRecordService.selectParkRecordDTOByLicense("晋E2011a");
        Assert.assertNotNull(parkRecordDTOs);
    }
}
