package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.AreaDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class areaServiceTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void selectById(){
        AreaDTO areaDTO = areaService.selectByPrimaryKey(1);
        Assert.assertTrue(areaDTO != null && "儿童乐园".equals(areaDTO.getAName()));
    }

    @Test
    public void insertAreaInfo(){
        AreaDTO areaDTO = new AreaDTO();

        areaDTO.setAName("罗马仕");
        areaDTO.setASort("时尚区区");

        int data = areaService.insert(areaDTO);

        Assert.assertEquals(5, data);

    }

    @Test
    public void updateAreaInfo(){
        AreaDTO areaDTO = areaService.selectByPrimaryKey(5);

        areaDTO.setASort("时尚区");

        int data = areaService.updateByPrimaryKeySelective(areaDTO);

        Assert.assertEquals(1, data);
    }

    @Test
    public void deleteAreaInfo(){
        areaService.deleteByPrimaryKey(3);
    }
}
