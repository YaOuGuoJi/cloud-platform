package com.yaouguoji.platform.impl;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.dto.CameraRecordDTO;
import com.yaouguoji.platform.entity.CameraRecordEntity;
import com.yaouguoji.platform.mapper.RecordMapper;
import com.yaouguoji.platform.service.CameraRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CameraRecordServiceImpl implements CameraRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraRecordServiceImpl.class);

    @Resource
    private RecordMapper recordMapper;

    @Override
    public List<CameraRecordDTO> batchSelectAllRecords(List<Integer> cameraIds) {
        List<CameraRecordEntity> entityList = recordMapper.batchSelectNewRecords(cameraIds);
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        List<CameraRecordDTO> cameraRecordDTOList = Lists.newArrayList();
        entityList.forEach(entity ->{
            CameraRecordDTO cameraRecordDTO = new CameraRecordDTO();
            BeanUtils.copyProperties(entity, cameraRecordDTO);
            cameraRecordDTOList.add(cameraRecordDTO);
        });
        return cameraRecordDTOList;
    }

    @Override
    public void deleteByPrimaryKey(Integer cRecodeId) {
        if (cRecodeId == 0){
            return;
        }
        recordMapper.deleteByPrimaryKey(cRecodeId);
        LOGGER.info("id为[{}]的摄像头记录已删除", cRecodeId);
    }

    @Override
    public int insert(CameraRecordDTO record) {
        if (record == null){
            return 0;
        }
        CameraRecordEntity recordEntity = new CameraRecordEntity();
        recordEntity.setCameraId(recordEntity.getCameraId());
        recordEntity.setCrNumber(recordEntity.getCrNumber());
        recordEntity.setCrAddTime(recordEntity.getCrAddTime());
        recordEntity.setCrUpdateTime(recordEntity.getCrUpdateTime());

        recordMapper.insert(recordEntity);
        return recordEntity.getCameraId();
    }

    @Override
    public CameraRecordDTO selectByPrimaryKey(Integer cRecodeId) {
        CameraRecordDTO cameraRecordDTO = new CameraRecordDTO();
        CameraRecordEntity cameraRecordEntity = recordMapper.selectByPrimaryKey(cRecodeId);
        if (cameraRecordEntity != null){
            BeanUtils.copyProperties(cameraRecordEntity,cameraRecordDTO);
        }
        return cameraRecordDTO;
    }

    @Override
    public int updateByPrimaryKeySelective(CameraRecordDTO record) {
        if (record == null){
            return 0;
        }
        CameraRecordEntity recordEntity = new CameraRecordEntity();
        BeanUtils.copyProperties(record, recordEntity);
        return recordMapper.updateByPrimaryKeySelective(recordEntity);
    }
}
