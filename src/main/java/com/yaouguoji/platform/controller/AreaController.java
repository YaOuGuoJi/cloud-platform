package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.enums.HttpStatus;
import com.yaouguoji.platform.impl.AreaServiceImpl;

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
    public CommonResult selectByPrimaryKey(@PathVariable("id") Integer areaId){
        AreaDTO areaDTO = areaServiceImpl.selectByPrimaryKey(areaId);
        if (areaDTO == null){
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }

        return CommonResult.success(areaDTO);
    }

    /**
     * 添加分区信息
     * @param aName
     * @param aSort
     * @return
     */
    @RequestMapping(value = "/insertArea",method = RequestMethod.POST)
    public CommonResult insertAreaInfo( @RequestParam("areaId") Integer areaId,
                                        @RequestParam("aName") String aName,
                                        @RequestParam("aSort") String aSort
                                        ){

        System.out.println("controllet的namme"+aName);
        System.out.println("con的id"+areaId);

        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setAreaId(areaId);
        areaDTO.setAName(aName);
        areaDTO.setASort(aSort);

        if (areaDTO == null ){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        int data = areaServiceImpl.insert(areaDTO);

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
    @DeleteMapping("/deleteAreaInfo/{id}")
    public CommonResult deleteById(@PathVariable("id") int id){
        if (id <= 0){
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }

        areaServiceImpl.deleteByPrimaryKey(id);
        return CommonResult.success();
    }
}
