package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.entity.*;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.impl.AreaServiceImpl;
import com.yaouguoji.platform.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creamer")
public class AreaController {

    @Autowired
    private AreaServiceImpl areaServiceImpl;

    /**
     * 根据id查询分区信息
     * @param areaId
     * @return
     */
    @GetMapping(value = "/selectArea/{id}")
    public CommonResult selectByPrimaryKey(@PathVariable("id") int areaId){
        AreaDTO areaDTO = areaServiceImpl.selectByPrimaryKey(areaId);
        if (areaDTO == null && areaDTO.equals("")){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }

        return CommonResult.success(areaDTO);
    }

    /**
     * 添加分区信息
     * @param recode
     * @return
     */
    @RequestMapping(value = "/insertArea",method = RequestMethod.POST)
    public CommonResult insertAreaInfo(@RequestBody AreaDTO recode){
        if (recode == null && recode.equals("")){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        int data = areaServiceImpl.insert(recode);

        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);

    }

    /**
     * 选择性修改分区信息
     * @param recode
     * @return
     */
    @RequestMapping(value = "/updateAreaInfo")
    public CommonResult updateAreaInfo(@RequestBody AreaDTO recode){
        if (recode == null && recode.equals("")){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        int data = areaServiceImpl.updateByPrimaryKeySelective(recode);
        return data > 0 ? CommonResult.success() : CommonResult.fail(HttpStatus.PARAMETER_ERROR);
    }

    /**
     * 根据id删除分区信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteAreaInfo")
    public CommonResult deleteById(@PathVariable("id") int id){
        if (id <= 0){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        areaServiceImpl.deleteByPrimaryKey(id);
        return CommonResult.success();
    }
}
