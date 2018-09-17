package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CarDTO;
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
    CarService carService;

    @Test
    public void testAddCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setLicense("晋E2032");
        carDTO.setOwnerId(0);
        carService.addCarDTO(carDTO);

    }

    @Test
    public void testUpdateCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(32);
        carDTO.setOwnerId(0);
        carDTO.setLicense("晋E2056");
        carService.updateCarDTO(carDTO);
    }

    @Test
    public void testDeleteCar() {
        carService.deleteCarDTO(32);
    }

    @Test
    public void testSelectCarById() {
        System.out.print(carService.selectCarDTOById(33));
    }

    @Test
    public void tetSelectCarByLicense() {
        CarDTO carDTO = carService.selectCarDTOByLicense("晋E2098");
        System.out.println(carDTO);
    }

    @Test
    public void testSelectCarAll() {
        List<CarDTO> carDTOs = carService.selectAll();
        for (CarDTO carDTO : carDTOs) {
            System.out.println(carDTO);
        }

    }
}
