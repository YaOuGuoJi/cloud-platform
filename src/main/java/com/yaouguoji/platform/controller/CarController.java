package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.CarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    private CarService carService;

    /**
     * 根据id查车
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/selectCarById/{id}")
    public CommonResult selectCarById(@PathVariable("id") Integer id) {
        CarDTO carDTO = carService.selectCarDTOById(id);
        if (carDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(carDTO);
    }

    /**
     * 根据车牌查车
     *
     * @param license
     * @return
     */
    @GetMapping(value = "/selectCarByLicense/{license}")
    public CommonResult selectCarByLicense(@PathVariable("license") String license) {
        CarDTO carDTO = carService.selectCarDTOByLicense(license);
        if (carDTO != null) {
            return CommonResult.success(carDTO);
        }
        return CommonResult.fail(HttpStatus.NOT_FOUND);
    }

    /**
     * 浏览所有车
     *
     * @return
     */
    @GetMapping(value = "/selectCarAll")
    public CommonResult selectCarAll() {
        List<CarDTO> carDTOs = carService.selectAll();
        if (carDTOs == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(carDTOs);
    }

    /**
     * 新增车辆信息
     *
     * @param ownerId
     * @param license
     * @return
     */
    @PostMapping("/addCar")
    public CommonResult addCar(Integer ownerId, String license) {
        if ((license.length() == 7 || license.length() == 8) && ownerId >= 0 && ownerId < 99999999999L) {
            CarDTO carDTO = new CarDTO();
            carDTO.setOwnerId(ownerId);
            carDTO.setLicense(license);
            int data = carService.addCarDTO(carDTO);
            return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    /**
     * 修改车信息
     *
     * @param id
     * @param ownerId
     * @param license
     * @return
     */
    @PostMapping("/updateCar")
    public CommonResult updateCar(Integer id, Integer ownerId, String license) {
        if ((license.length() == 7 || license.length() == 8) && ownerId >= 0 && ownerId < 99999999999L) {
            CarDTO carDTO = new CarDTO();
            carDTO.setId(id);
            carDTO.setLicense(license);
            carDTO.setOwnerId(ownerId);
            int data = carService.updateCarDTO(carDTO);
            return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }
}
