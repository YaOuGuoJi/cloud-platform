package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CarDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {
    @Resource
    private CarService carService;

    @Test
    public void testAddCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setLicense("晋E2035A");
        carDTO.setOwnerId(0);
        Assert.assertEquals(1,carService.addCarDTO(carDTO));

    }

    @Test
    public void testUpdateCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(19);
        carDTO.setOwnerId(0);
        carDTO.setLicense("晋E2036A");
        Assert.assertEquals(1,carService.updateCarDTO(carDTO));

    }

    @Test
    public void testDeleteCar() {
        Assert.assertEquals(1,carService.deleteCarDTO(32));
    }

    @Test
    public void testSelectCarById() {
        Assert.assertNotNull(carService.selectCarDTOById(19));
    }

    @Test
    public void tetSelectCarByLicense() {
        Assert.assertNotNull(carService.selectCarDTOByLicense("晋E2032A"));
    }

    @Test
    public void testSelectCarAll() {
        Assert.assertNotNull(carService.selectAll());
    }
}
