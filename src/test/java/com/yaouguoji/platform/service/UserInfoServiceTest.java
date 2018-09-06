package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserInfoDTO;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {

    @Resource
    private UserInfoService userInfoService;

    @Test
    public void testAdd() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(0);
        userInfoDTO.setUserName("刘文");
        userInfoDTO.setSex(1);
        userInfoDTO.setBirthday(new DateTime(2001, 12, 12, 10, 0, 0).toDate());
        userInfoDTO.setPhone("18799342345");
        userInfoDTO.setEmail("28384424@qq.com");
        userInfoDTO.setAddress("西安市未央区未央路198号");
        userInfoDTO.setJob("某公司码农");
        userInfoDTO.setCarId(1);
        userInfoDTO.setVip(1);
        int userId = userInfoService.addUserInfo(userInfoDTO);
        Assert.assertTrue(userId > 0);

    }

    @Test
    public void testSelectAndUpdate() {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserId(100000000);
        Assert.assertNotNull(userInfoDTO);
        userInfoDTO.setUserName("大师傅说付或所付或或或或或");
        int updateResult = userInfoService.updateUserInfo(userInfoDTO);
        Assert.assertTrue(updateResult == 1);
    }
}
