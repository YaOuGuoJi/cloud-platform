package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.CarDTO;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.service.CarService;
import com.yaouguoji.platform.util.CarUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CarController {

    @Resource
    private CarService carService;

    /**
     * 根据id查车
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/car/{id}")
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
    @GetMapping(value = "/car/license")
    public CommonResult selectCarByLicense(String license) {
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
    @GetMapping(value = "/car/all")
    public CommonResult selectCarAll() {
        List<CarDTO> carDTOs = carService.selectAll();
        if (CollectionUtils.isEmpty(carDTOs)) {
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
    @PostMapping("/car")
    public CommonResult addCar(Integer ownerId, String license, @RequestParam(required = false) Integer id) {
        if (!CarUtils.checkLicense(license) || ownerId <= 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        int data;
        CarDTO carDTO = new CarDTO();
        carDTO.setOwnerId(ownerId);
        carDTO.setLicense(license);
        if (id > 0 && carService.selectCarDTOById(id) != null) {
            carDTO.setId(id);
            data = carService.updateCarDTO(carDTO);
        } else {
            data = carService.addCarDTO(carDTO);
        }
        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);

    }
}
