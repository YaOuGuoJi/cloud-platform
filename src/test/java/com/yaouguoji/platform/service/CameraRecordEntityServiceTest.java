package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CameraRecordDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CameraRecordEntityServiceTest {

    @Autowired
    private CameraRecordService cameraRecordService;

    @Test
    public void selectById(){
        CameraRecordDTO cameraRecordDTO = cameraRecordService.selectByPrimaryKey(2);

        Assert.assertTrue(cameraRecordDTO != null && 54 == cameraRecordDTO.getCrNumber());
    }

    @Test
    public void inserRecordInfo(){
        CameraRecordDTO cameraRecordDTO = new CameraRecordDTO();

        cameraRecordDTO.setCameraId(2);
        cameraRecordDTO.setCrNumber(55);

        int data = cameraRecordService.insert(cameraRecordDTO);

        Assert.assertEquals(2, data);

    }

    @Test
    public void updateRecordInfo(){
        CameraRecordDTO cameraRecordDTO = cameraRecordService.selectByPrimaryKey(4);

        System.out.println("查询到的记录人数"+cameraRecordDTO.getCrNumber());
        cameraRecordDTO.setCrNumber(22);

        int data = cameraRecordService.updateByPrimaryKeySelective(cameraRecordDTO);
        System.out.println("修改后的data"+data);

        Assert.assertEquals(1, data);
    }

    @Test
    public void deleteById(){
        cameraRecordService.deleteByPrimaryKey(7);
    }
}
