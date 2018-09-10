package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.dto.ParkRecordDTO;
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
    @Resource
    ParkRecordService parkRecordService;
    @Test
    public void testAddCar(){
        CarDTO carDTO=new CarDTO();
        carDTO.setLicense("晋E2056");
        carDTO.setOwnerId(0);
        carService.addCarDTO(carDTO);

    }
    @Test
    public void testupdateCar(){
        CarDTO carDTO=new CarDTO();
        carDTO.setId(32);
        carDTO.setOwnerId(0);
        carDTO.setLicense("晋E2056");
        carService.updateCarDTO(carDTO);
    }

    @Test
    public void testdeleteCar(){
        carService.deleteCarDTO(14);
    }
    @Test
    public void testselectcarI(){
        System.out.print(carService.selectCarDTOI(17));
    }
    @Test
    public void testselectcarl() {
        CarDTO carDTO = carService.selectCarDTOL("晋E1997");
            System.out.println(carDTO);
    }
    @Test
    public void testselectcarall(){
        List<CarDTO> carDTOS = carService.selectAll();
        for(Iterator<CarDTO> iterator = carDTOS.iterator();iterator.hasNext();){
            CarDTO carDTO = (CarDTO) iterator.next();
            System.out.println(carDTO);
        }

    }
}
