package com.yaouguoji.platform.impl;

import com.google.common.collect.Maps;
import com.yaouguoji.platform.dto.UserPriceCountDTO;
import com.yaouguoji.platform.entity.UserFrequencyEntity;
import com.yaouguoji.platform.entity.UserPriceCountEntity;
import com.yaouguoji.platform.entity.UserSexAndAgeEntity;
import com.yaouguoji.platform.mapper.ShopUserAnalysisMapper;
import com.yaouguoji.platform.service.ShopUserAnalysisService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShopUserAnalysisServiceImpl implements ShopUserAnalysisService {

    @Resource
    private ShopUserAnalysisMapper shopUserAnalysisMapper;

    @Override
    public Map<String, Map<String, Integer>> selectAgeAndSexCount(Integer shopId, Date startTime, Date endTime) {
        final int[] ageArray = {0, 18, 45, 65};
        List<UserSexAndAgeEntity> ageAndSexEntities = shopUserAnalysisMapper.ageAndSexSplit(shopId, startTime, endTime);
        if (CollectionUtils.isEmpty(ageAndSexEntities)) {
            return Maps.newHashMap();
        }
        Map<String, Map<String, Integer>> ageAndSex = Maps.newHashMap();
        Map<String, Integer> maleAgeSplit = Maps.newHashMap();
        Map<String, Integer> femaleAgeSplit = Maps.newHashMap();
        ageAndSex.put("male", maleAgeSplit);
        ageAndSex.put("female", femaleAgeSplit);
        for (UserSexAndAgeEntity userSexAndAgeEntity : ageAndSexEntities) {
            String ageRegion = this.regionName(ageArray, userSexAndAgeEntity.getAge());
            if (userSexAndAgeEntity.getSex() == 1) {
                maleAgeSplit.put(ageRegion, maleAgeSplit.getOrDefault(ageRegion, 0) + 1);
            } else if (userSexAndAgeEntity.getSex() == 2) {
                femaleAgeSplit.put(ageRegion, maleAgeSplit.getOrDefault(ageRegion, 0) + 1);
            }
        }
        return ageAndSex;
    }

    @Override
    public UserPriceCountDTO selectUserPriceCount(Integer shopId, Date startTime, Date endTime) {
        // 统计消费分布上下浮动百分比
        final BigDecimal ratio = new BigDecimal("0.30");
        UserPriceCountDTO userPriceCountDTO = new UserPriceCountDTO();
        Map<String, BigDecimal> priceMap = shopUserAnalysisMapper.averageAndTotalPrice(shopId, startTime, endTime);
        if (CollectionUtils.isEmpty(priceMap)) {
            return userPriceCountDTO;
        }
        BigDecimal averagePrice = priceMap.get("averagePrice").setScale(2, RoundingMode.HALF_UP);
        BigDecimal low = averagePrice.multiply(new BigDecimal("1.00").subtract(ratio));
        BigDecimal high = averagePrice.multiply(new BigDecimal("1.00").add(ratio));
        UserPriceCountEntity userPriceCount = shopUserAnalysisMapper.userPriceCount(shopId, startTime, endTime, low, high);
        userPriceCountDTO.setAveragePrice(averagePrice);
        userPriceCountDTO.setTotalPrice(priceMap.get("totalPrice"));
        BeanUtils.copyProperties(userPriceCount, userPriceCountDTO.getDistributed());
        return userPriceCountDTO;
    }

    @Override
    public Map<String, Object> selectUserFrequencyCount(Integer shopId, Date startTime, Date endTime) {
        // 统计消费次数上下依据平均消费次数浮动百分比,最小为0.15，最大为0.875
        // 根据上述百分比将统计分为三个阶段，放在如下数组中
        int[] frequencySplit = new int[3];
        Map<String, Object> userFrequencyCount = Maps.newHashMap();
        Map<String, BigDecimal> frequencyMap = shopUserAnalysisMapper.averageAndTotalFrequency(shopId, startTime, endTime);
        BigDecimal averageFrequency = frequencyMap.get("averageFrequency").setScale(2, RoundingMode.HALF_UP);
        this.constructFrequency(frequencySplit, averageFrequency);
        List<UserFrequencyEntity> userFrequencyEntities = shopUserAnalysisMapper.userFrequencyCount(shopId, startTime, endTime);
        if (CollectionUtils.isEmpty(userFrequencyEntities)) {
            return userFrequencyCount;
        }
        Map<String, Integer> distributed = Maps.newHashMap();
        for (UserFrequencyEntity userFrequencyEntity : userFrequencyEntities) {
            String frequencyName = regionName(frequencySplit, userFrequencyEntity.getFrequency());
            distributed.put(frequencyName, distributed.getOrDefault(frequencyName, 0));
        }
        userFrequencyCount.put("totalFrequency", frequencyMap.get("totalFrequency"));
        userFrequencyCount.put("averageFrequency", averageFrequency);
        userFrequencyCount.put("distributed", distributed);
        return userFrequencyCount;
    }

    private String regionName(int[] array, int value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < array.length; i++) {
            if (value < array[i]) {
                return sb.append(array[i - 1]).append("-").append(array[i] - 1).toString();
            }
        }
        return sb.append(array[array.length - 1]).append("more").toString();
    }

    private void constructFrequency(int[] arr, BigDecimal average) {
        final BigDecimal ratio = new BigDecimal("0.30");
        BigDecimal compared = new BigDecimal("4.00");
        if (average == null || average.compareTo(compared) < 0) {
            arr[0] = 0;
            arr[1] = 3;
            arr[2] = 6;
        } else {
            arr[0] = 0;
            arr[1] = average.multiply(new BigDecimal("1.00").subtract(ratio))
                    .setScale(0, RoundingMode.HALF_UP).intValue();
            arr[2] = average.multiply(new BigDecimal("1.00").add(ratio))
                    .setScale(0, RoundingMode.HALF_UP).intValue();
        }
    }

}
