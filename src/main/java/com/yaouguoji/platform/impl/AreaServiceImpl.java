package com.yaouguoji.platform.impl;

import com.google.common.collect.Lists;
import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.entity.AreaEntity;
import com.yaouguoji.platform.mapper.AreaMapper;
import com.yaouguoji.platform.service.AreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Service
public class AreaServiceImpl implements AreaService{

    @Resource
    private AreaMapper areaMapper;


    @Override
    public List<AreaDTO> selectAll() {
        List<AreaEntity> entityList = areaMapper.selectAll();
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        List<AreaDTO> areaDTOList = Lists.newArrayList();
        entityList.forEach(entity ->{
            AreaDTO areaDTO = new AreaDTO();
            BeanUtils.copyProperties(entity, areaDTO);
            areaDTOList.add(areaDTO);
        });
        return areaDTOList;
    }

    @Override
    public void deleteByPrimaryKey(Integer areaId) {
        if (areaId == 0){
            return;
        }

        areaMapper.deleteByPrimaryKey(areaId);
        LOGGER.info("id为"+areaId+"de分区信息已删除");
    }

    @Override
    public int insert(AreaDTO record) {
        if (record == null){
            return 0;
        }

        AreaEntity area = new AreaEntity();
        area.setaName(record.getAName());
        area.setaSort(record.getASort());

        areaMapper.insert(area);
        return area.getAreaId();
    }

    @Override
    public int insertSelective(AreaDTO record) {
        return 0;
    }

    @Override
    public AreaDTO selectByPrimaryKey(Integer areaId) {
        AreaDTO areaDTO = new AreaDTO();
        AreaEntity area = areaMapper.selectByPrimaryKey(areaId);

        if (area != null){
            BeanUtils.copyProperties(area,areaDTO);
        }

        return areaDTO;
    }

    @Override
    public int updateByPrimaryKeySelective(AreaDTO record) {
        if (record == null){
            return 0;
        }

        AreaEntity area = new AreaEntity();
        BeanUtils.copyProperties(record,area);

        return areaMapper.updateByPrimaryKeySelective(area);
    }

    @Override
    public int updateByPrimaryKey(AreaDTO record) {
        return 0;
    }
}
