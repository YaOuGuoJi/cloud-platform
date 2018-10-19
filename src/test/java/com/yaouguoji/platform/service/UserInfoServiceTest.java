package com.yaouguoji.platform.service;

import com.yaouguoji.platform.dto.UserInfoDTO;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {

    @Resource
    private UserInfoService userInfoService;

    @Test
    public void testAdd() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(6);
        userInfoDTO.setUserName("user7");
        userInfoDTO.setSex(1);
        userInfoDTO.setBirthday(new DateTime(2002, 12, 12, 10, 0, 0).toDate());
        userInfoDTO.setPhone("18799342345");
        userInfoDTO.setEmail("28384424@qq.com");
        userInfoDTO.setAddress("西安市未央区");
        userInfoDTO.setJob("行政");
        userInfoDTO.setCarId(1);
        userInfoDTO.setVip(1);
        userInfoDTO.setIdentityId("0");

        int userId = userInfoService.addUserInfo(userInfoDTO);
        Assert.assertTrue(userId > 0);
    }

    @Test
    public void testAddUserInfo(){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        String[] phones={"13","17","15","18","16"};
        String[] address={"未央区","莲湖区","高新区","灞桥区","临潼区"};
        for (int i = 7; i < 27; i++){
            Random rd = new Random();
            userInfoDTO.setUserId(i);
            userInfoDTO.setUserName("user"+i);
            int sex = rd.nextInt(2)+1;
            userInfoDTO.setSex(sex);
            int year = rd.nextInt(9)+1;
            int month = rd.nextInt(12)+1;
            int day = rd.nextInt(30)+1;
            userInfoDTO.setBirthday(new DateTime(Integer.parseInt("199"+year),month,day,0,0,0).toDate());
            userInfoDTO.setPhone(phones[(int)(Math.random()*4)]+(int)(Math.random()*10)+"*****"+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10));
            int email = rd.nextInt(1000000000)+1;
            userInfoDTO.setEmail(email+"@qq.com");
            userInfoDTO.setAddress("西安市"+address[(int)(Math.random()*address.length)]);
            userInfoDTO.setJob("worker"+i);
            int carId = rd.nextInt(100)+1;
            userInfoDTO.setCarId(carId);
            userInfoDTO.setVip(1);
            userInfoDTO.setIdentityId("0");
            int userId = userInfoService.addUserInfo(userInfoDTO);
            Assert.assertTrue(userId > 0);
        }

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
