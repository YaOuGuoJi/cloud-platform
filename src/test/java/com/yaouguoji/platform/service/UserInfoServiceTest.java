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
        String[] phones = {"13","17","15","18","16"};
        String[] address = {"未央区","莲湖区","高新区","灞桥区","临潼区","高陵区","碑林区","长安区","阎良区","新城区"};
        String[] names = {"李","王","张","刘","陈","杨","赵","黄","周",
                "吴","徐","孙","胡","朱","高","林","何","郭","马","罗","董","于","冯",
                "萧","谢","梁","宋","郑","唐","韩","曾","吕","沈","阎","叶","彭","薛","傅","邓","夏",
                "许","袁","曹","程","丁","姜","苏","潘","魏","杜","田","余","蒋","卢","任","汪","贾"};
        String[] lastNames = {"*","**","***"};
        for (int i = 1960; i < 2000; i++){
            Random rd = new Random();
            userInfoDTO.setUserId(i);
            userInfoDTO.setUserName(names[(int)(Math.random()*names.length)]+lastNames[(int)(Math.random()*2)]);
            int sex = rd.nextInt(2)+1;
            userInfoDTO.setSex(sex);
            int years = (int)(Math.random()*(2018-1949))+1949;
            int month = rd.nextInt(12)+1;
            int day = rd.nextInt(30)+1;
            userInfoDTO.setBirthday(new DateTime(years,month,day,0,0,0).toDate());
            userInfoDTO.setPhone(phones[(int)(Math.random()*4)]+(int)(Math.random()*10)+"*****"+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10));
            int email = rd.nextInt(1000000000)+1;
            userInfoDTO.setEmail(email+"@qq.com");
            userInfoDTO.setAddress("西安市"+address[(int)(Math.random()*address.length)]);
            userInfoDTO.setJob("worker"+i);
            userInfoDTO.setCarId(100+i);
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
