package com.yaouguoji.platform.controller;

import com.yaouguoji.platform.common.CommonResult;
import com.yaouguoji.platform.dto.CameraDTO;
import com.yaouguoji.platform.impl.CameraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CameraController {

    @Autowired
    private CameraServiceImpl cameraService;

//    public CommonResult selectById(@PathVariable("cameraId") int cameraId){
//        CameraDTO cameraDTO = new CameraDTO();
//
//    }
}
