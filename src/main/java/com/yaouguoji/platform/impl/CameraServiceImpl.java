package com.yaouguoji.platform.impl;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.dto.CameraDTO;
import com.yaouguoji.platform.entity.CameraEntity;
import com.yaouguoji.platform.mapper.CameraMapper;
import com.yaouguoji.platform.service.CameraService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CameraServiceImpl implements CameraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraServiceImpl.class);

    @Resource
    private CameraMapper cameraMapper;

    @Override
    public List<CameraDTO> selectByCameraIds() {
        List<CameraEntity> entityList = cameraMapper.selectAll();
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        List<CameraDTO> cameraDTOSList = Lists.newArrayList();
        entityList.forEach(entity -> {
            CameraDTO cameraDTO = new CameraDTO();
            BeanUtils.copyProperties(entity, cameraDTO);
            cameraDTOSList.add(cameraDTO);
        });
        return cameraDTOSList;
    }

    @Override
    public void deleteByPrimaryKey(Integer cameraId) {
        if (cameraId == 0) {
            return;
        }
        cameraMapper.deleteByPrimaryKey(cameraId);
        LOGGER.info("id为[{}]的摄像头信息已删除", cameraId);
    }

    @Override
    public int insert(CameraDTO record) {
        if (record == null || record.getAreaId() <= 0
                || record.getCameraId() <= 0 || StringUtils.isBlank(record.getCName())) {
            return 0;
        }
        CameraEntity camera = new CameraEntity();
        camera.setAreaId(record.getAreaId());
        camera.setcName(record.getCName());
        camera.setcIp(record.getCIp());
        cameraMapper.insert(camera);
        return camera.getCameraId();
    }

    @Override
    public CameraDTO selectByPrimaryKey(Integer cameraId) {
        CameraEntity camera = cameraMapper.selectByPrimaryKey(cameraId);
        if (camera == null) {
            return null;
        }
        CameraDTO cameraDTO = new CameraDTO();
        BeanUtils.copyProperties(camera, cameraDTO);
        return cameraDTO;
    }

    @Override
    public int updateByPrimaryKeySelective(CameraDTO record) {
        if (record == null) {
            return 0;
        }
        CameraEntity camera = new CameraEntity();
        BeanUtils.copyProperties(record, camera);
        return cameraMapper.updateByPrimaryKeySelective(camera);
    }
}
