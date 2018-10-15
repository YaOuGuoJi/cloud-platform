package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.UserPriceCountDTO;
import com.yaouguoji.platform.entity.UserFrequencyEntity;
import com.yaouguoji.platform.entity.UserPriceCountEntity;
import com.yaouguoji.platform.entity.UserSexAndAgeEntity;
import com.yaouguoji.platform.mapper.UserFrequencyMapper;
import com.yaouguoji.platform.mapper.UserPriceMapper;
import com.yaouguoji.platform.mapper.UserSexAndAgeMapper;
import com.yaouguoji.platform.service.ShowUserInfoToShop;
import com.yaouguoji.platform.util.ShowUserInfoToShopUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowUserInfoToShopImpl implements ShowUserInfoToShop {

    @Resource
    private UserSexAndAgeMapper userAgeAndSexSplitMapper;
    @Resource
    private UserPriceMapper userPriceMapper;
    @Resource
    private UserFrequencyMapper userFrequencyMapper;

    @Override
    public Map selectAgeAndSexSplit(Integer shopId, Date startTime, Date endTime) {
        //    年龄分段数组，总个数任意，第一个建议为0，必须按照从小到大排
        final int age[] = {0, 17, 45, 65};
        List<UserSexAndAgeEntity> userAgeAndSexSplitEntities = userAgeAndSexSplitMapper.selectAgeAndSexSplitEntityMapper(shopId, startTime, endTime);
        Map<String, Map<String, Integer>> ageAndSex = new HashMap();
        Map<String, Integer> maleAgeSplit = new HashMap<>();
        Map<String, Integer> femaleAgeSplit = new HashMap<>();
        //根据age数组创建分段名称并初始化
        for (int i = 0; i < age.length; i++) {
            maleAgeSplit.put(ShowUserInfoToShopUtil.nameSplit(age, i), 0);
            femaleAgeSplit.put(ShowUserInfoToShopUtil.nameSplit(age, i), 0);
        }
        ageAndSex.put("male", maleAgeSplit);
        ageAndSex.put("female", femaleAgeSplit);
        if (CollectionUtils.isEmpty(userAgeAndSexSplitEntities)) {
            return ageAndSex;
        }
        for (UserSexAndAgeEntity userSexAndAgeEntity : userAgeAndSexSplitEntities) {
            for (int i = 0; i < age.length; i++) {
                String ages = ShowUserInfoToShopUtil.nameSplit(age, i);
                if (i == age.length - 1) {
                    ShowUserInfoToShopUtil.setSexAndAge(i, ageAndSex, userSexAndAgeEntity, ages);
                    break;
                }
                if (userSexAndAgeEntity.getAge() <= age[i + 1]) {
                    ShowUserInfoToShopUtil.setSexAndAge(i, ageAndSex, userSexAndAgeEntity, ages);
                    break;
                }
            }
        }
        return ageAndSex;
    }

    @Override
    public UserPriceCountDTO selectUserPriceCount(Integer shopId, Date startTime, Date endTime) {
        //统计消费分布上下浮动百分比
        final Double ratio = 0.3;
        //创建返回对象，并初始化所有值为0
        UserPriceCountDTO userPriceCountDTO = new UserPriceCountDTO(new BigDecimal("0.00"), new BigDecimal(0.00), 0, 0, 0);
        Map userAverageAndTotalPrice = userPriceMapper.selectUserAverageAndTotalPrice(shopId, startTime, endTime);
        if (CollectionUtils.isEmpty(userAverageAndTotalPrice)) {
            return userPriceCountDTO;
        }
        userPriceCountDTO.setAveragePrice((BigDecimal) userAverageAndTotalPrice.get("averagePrice"));
        userPriceCountDTO.setTotalPrice((BigDecimal) userAverageAndTotalPrice.get("totalPrice"));
        UserPriceCountEntity userPriceCountEntity = userPriceMapper.selectUserPriceSplit(shopId, startTime, endTime, userPriceCountDTO.getAveragePrice(), ratio);
        if (userPriceCountEntity == null) {
            return userPriceCountDTO;
        }
        BeanUtils.copyProperties(userPriceCountEntity, userPriceCountDTO);
        return userPriceCountDTO;
    }

    @Override
    public Map<String, Object> selectUserFrequencyCount(Integer shopId, Date startTime, Date endTime) {
        //统计消费次数上下依据平均消费次数浮动百分比,最小为0.15，最大为0.875
        final double ratio = 0.3;
        //根据上述百分比将统计分为三个阶段，放在如下数组中
        int frequencySplit[] = new int[3];
        Map<String, Object> userFrequencyCount = new HashMap<>();
        userFrequencyCount.put("totalFrequency", 0);
        userFrequencyCount.put("averageFrequency", 0);
        Map userTotalAndAverageFrequency = userFrequencyMapper.selectUserTotalAndAverageFrequency(shopId, startTime, endTime);
        BigDecimal averageFrequency = (BigDecimal) userTotalAndAverageFrequency.get("averageFrequency");
        ShowUserInfoToShopUtil.setFrequency(frequencySplit, averageFrequency, ratio);
        for (int i = 0; i < frequencySplit.length; i++) {
            userFrequencyCount.put(ShowUserInfoToShopUtil.nameSplit(frequencySplit, i), 0);
        }
        List<UserFrequencyEntity> userFrequencyEntities = userFrequencyMapper.selectUserFrequency(shopId, startTime, endTime);
        if (CollectionUtils.isEmpty(userFrequencyCount) || CollectionUtils.isEmpty(userFrequencyCount)) {
            return userFrequencyCount;
        }
        userFrequencyCount.put("totalFrequency", userTotalAndAverageFrequency.get("totalFrequency"));
        userFrequencyCount.put("averageFrequency", userTotalAndAverageFrequency.get("averageFrequency"));
        for (UserFrequencyEntity userFrequencyEntity : userFrequencyEntities) {
            for (int i = 0; i < frequencySplit.length; i++) {
                String frequencyName = ShowUserInfoToShopUtil.nameSplit(frequencySplit, i);
                if (i == frequencySplit.length - 1) {
                    userFrequencyCount.put(frequencyName, (int) userFrequencyCount.get(frequencyName) + userFrequencyEntity.getNumberOfPeople());
                    break;
                }
                if (userFrequencyEntity.getFrequency() < frequencySplit[i + 1]) {
                    userFrequencyCount.put(frequencyName, (int) userFrequencyCount.get(frequencyName) + userFrequencyEntity.getNumberOfPeople());
                    break;
                }

            }
        }
        return userFrequencyCount;
    }
}
