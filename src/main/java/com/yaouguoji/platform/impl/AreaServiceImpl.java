package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.AreaDTO;
import com.yaouguoji.platform.entity.area;
import com.yaouguoji.platform.mapper.areaMapper;
import com.yaouguoji.platform.service.AreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Service
public class AreaServiceImpl implements AreaService{

    @Autowired
    private areaMapper areaMapper;


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
        System.out.println("name"+record.getAName());

        area area = new area();
        area.setaName(record.getAName());
        area.setaSort(record.getASort());

        areaMapper.insert(area);
        System.out.println("id为"+area.getAreaId());
        return area.getAreaId();
    }

    @Override
    public int insertSelective(AreaDTO record) {
        return 0;
    }

    @Override
    public AreaDTO selectByPrimaryKey(Integer areaId) {
        AreaDTO areaDTO = new AreaDTO();
        area area = areaMapper.selectByPrimaryKey(areaId);

        if (area != null){
            BeanUtils.copyProperties(area,areaDTO);
        }

        return areaDTO;
    }

    @Override
    public int updateByPrimaryKeySelective(AreaDTO record) {
        if (record == null && record.equals("")){
            return 0;
        }

        area area = new area();
        BeanUtils.copyProperties(record,area);

        return areaMapper.updateByPrimaryKeySelective(area);
    }

    @Override
    public int updateByPrimaryKey(AreaDTO record) {
        return 0;
    }
}
