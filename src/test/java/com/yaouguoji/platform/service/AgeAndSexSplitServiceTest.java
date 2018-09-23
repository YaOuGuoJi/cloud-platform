package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserAgeAndSexSplitDTO;
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
public class AgeAndSexSplitServiceTest {
    @Resource
    private UserAgeAndSexSplitService userAgeAndSexSplitService;

    @Test
    public void selectAgeAndSexSplitTest() {
        Date startTime = new Date("2014/01/01");
        Date endTime = new Date();
        List<UserAgeAndSexSplitDTO> userAgeAndSexSplitDTOs = userAgeAndSexSplitService.selectAgeAndSexSplit(100001, startTime, endTime);
        Assert.assertNotNull(userAgeAndSexSplitDTOs);
    }
}
