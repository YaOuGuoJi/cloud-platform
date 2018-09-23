package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.UserAgeAndSexSplitEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgeAndSexSplitTest {
    @Resource
    private UserAgeAndSexSplitMapper userAgeAndSexSplitMapper;
    @Test
    public void selectAgeAndtSplittest(){
        Date startTime=new Date("2014/01/01");
        Date endTime=new Date();
        List<UserAgeAndSexSplitEntity> userAgeAndSexSplitEntities = userAgeAndSexSplitMapper.selectAgeAndSexSplitEntityMapper(100001, startTime, endTime);
        Assert.assertNotNull(userAgeAndSexSplitEntities);
    }

}
