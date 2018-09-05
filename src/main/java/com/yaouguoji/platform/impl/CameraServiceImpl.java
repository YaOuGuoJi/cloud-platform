package com.yaouguoji.platform.impl;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.dto.CameraDTO;
import com.yaouguoji.platform.entity.CameraEntity;
import com.yaouguoji.platform.mapper.CameraMapper;
import com.yaouguoji.platform.service.CameraService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Service
public class CameraServiceImpl implements CameraService {

    @Resource
    private CameraMapper cameraMapper;

    @Override
    public List<CameraDTO> selectByCameraIds() {
        List<CameraEntity> entityList = cameraMapper.selectAll();
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        List<CameraDTO> cameraDTOSList = Lists.newArrayList();
        entityList.forEach(entity ->{
            CameraDTO cameraDTO = new CameraDTO();
            BeanUtils.copyProperties(entity, cameraDTO);
            cameraDTOSList.add(cameraDTO);
        });
        return cameraDTOSList;
    }

    @Override
    public void deleteByPrimaryKey(Integer cameraId) {
        if (cameraId == 0){
            return;
        }

        cameraMapper.deleteByPrimaryKey(cameraId);
        LOGGER.info("id为"+cameraId+"的摄像头信息已删除");
    }

    @Override
    public int insert(CameraDTO record) {
        if (record != null && record.equals("")){
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
    public int insertSelective(CameraDTO record) {
        return 0;
    }

    @Override
    public CameraDTO selectByPrimaryKey(Integer cameraId) {
        CameraDTO cameraDTO = new CameraDTO();
        CameraEntity camera = cameraMapper.selectByPrimaryKey(cameraId);

        if (camera != null){
            BeanUtils.copyProperties(camera,cameraDTO);
        }

        return cameraDTO;
    }

    @Override
    public int updateByPrimaryKeySelective(CameraDTO record) {

        if (record != null && record.equals("")){
            return 0;
        }

        CameraEntity camera = new CameraEntity();
        BeanUtils.copyProperties(record,camera);

        return cameraMapper.updateByPrimaryKeySelective(camera);

    }

    @Override
    public int updateByPrimaryKey(CameraDTO record) {
        return 0;
    }

}
