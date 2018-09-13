package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.CarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/CarController")
public class CarController {
    @Resource
    CarService carService;
    /**
     *根据id查车
     */
    @GetMapping(value = "/selectCarById/{id}")
   public CommonResult selectCarById(@PathVariable("id") Integer id){
        CarDTO carDTO = carService.selectCarDTOById(id);
        if (carDTO == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(carDTO);
    }

    /**
     * 根据车牌查车
     * @param license
     * @return
     */
    @GetMapping(value = "/selectCarByLicense")
    public CommonResult selectCarByLicense(@PathVariable("license") String license){
        CarDTO carDTO=carService.selectCarDTOByL(license);
        if (carDTO == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(carDTO);
    }

    /**
     * 浏览所有车
     * @return
     */
    @GetMapping(value = "/selectCarAll")
    public CommonResult selectCarAll(){
        List<CarDTO> carDTOS = carService.selectAll();
        if (carDTOS == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(carDTOS);
    }

    /**
     * 新增车辆信息
     * @param ownerId
     * @param License
     * @return
     */
    @PostMapping("/addCar")
    public CommonResult addCar(@PathVariable("ownerId") Integer ownerId,
                               @PathVariable("license") String License){
        CarDTO carDTO=new CarDTO();
        carDTO.setOwnerId(ownerId);
        carDTO.setLicense(License);
        carService.addCarDTO(carDTO);
        if (carDTO== null ){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int data = carService.addCarDTO(carDTO);
        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }
    }
