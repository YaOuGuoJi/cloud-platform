package com.yaouguoji.platform.impl;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.dto.CameraRecordDTO;
import com.yaouguoji.platform.entity.CameraRecord;
import com.yaouguoji.platform.mapper.RecodeMapper;
import com.yaouguoji.platform.service.CameraRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Service
public class CameraRecoedServiceImpl implements CameraRecordService {

    @Resource
    private RecodeMapper recodeMapper;

    @Override
    public List<CameraRecordDTO> selectAlls(List<Integer> cameraIds) {
        List<CameraRecord> entityList = recodeMapper.selectAlls(cameraIds);
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

        recodeMapper.deleteByPrimaryKey(cRecodeId);

        LOGGER.info("id为"+cRecodeId+"的摄像头记录已删除");
    }

    @Override
    public int insert(CameraRecordDTO record) {
        if (record == null){
            return 0;
        }

        CameraRecord recode = new CameraRecord();

        recode.setCameraId(record.getCameraId());
        recode.setCrNumber(record.getCrNumber());
        recode.setCrAddTime(record.getCrAddTime());
        recode.setCrUpdateTime(record.getCrUpdateTime());

        recodeMapper.insert(recode);

        return recode.getCameraId();
    }

    @Override
    public int insertSelective(CameraRecordDTO record) {
        return 0;
    }

    @Override
    public CameraRecordDTO selectByPrimaryKey(Integer cRecodeId) {

        CameraRecordDTO cameraRecordDTO = new CameraRecordDTO();

        CameraRecord recode = recodeMapper.selectByPrimaryKey(cRecodeId);

        if (recode != null){
            BeanUtils.copyProperties(recode,cameraRecordDTO);
        }

        return cameraRecordDTO;
    }

    @Override
    public int updateByPrimaryKeySelectives(CameraRecordDTO record) {
        if (record == null){
            return 0;
        }

        CameraRecord recode = new CameraRecord();
        BeanUtils.copyProperties(record,recode);

        return recodeMapper.updateByPrimaryKeySelective(recode);
    }

    @Override
    public int updateByPrimaryKey(CameraRecordDTO record) {
        return 0;
    }
}
