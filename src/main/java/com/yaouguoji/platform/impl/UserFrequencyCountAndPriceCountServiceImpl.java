package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.dto.UserFrequencyCountAndPriceCountDTO;
import com.yaouguoji.platform.entity.UserFrequencyCountAndPriceCountEntity;
import com.yaouguoji.platform.mapper.UserFrequencyCountAndPriceCountMapper;
import com.yaouguoji.platform.service.UserFrequencyCountAndPriceCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class UserFrequencyCountAndPriceCountServiceImpl implements UserFrequencyCountAndPriceCountService {
    @Resource
    UserFrequencyCountAndPriceCountMapper userFrequencyCountAndPriceCountMapper;

    @Override
    public UserFrequencyCountAndPriceCountDTO selectUserFrequencyCountAndPriceCount(Integer shopId, Date startTime, Date endTime) {
        List<UserFrequencyCountAndPriceCountEntity> userFrequencyCountAndPriceCountEntities = userFrequencyCountAndPriceCountMapper.selectUserFrequencyCountAndPriceCount(shopId, startTime, endTime);
        if (userFrequencyCountAndPriceCountEntities != null) {
//            消费人数
            BigDecimal userCount = new BigDecimal(0);
//            平均消费水平
            BigDecimal average = new BigDecimal(0);
//            总的消费钱
            BigDecimal countPrice = new BigDecimal(0);
            for (UserFrequencyCountAndPriceCountEntity userFrequencyCountAndPriceCountEntity : userFrequencyCountAndPriceCountEntities) {
                countPrice = countPrice.add(userFrequencyCountAndPriceCountEntity.getPriceCount());
                userCount = userCount.add(new BigDecimal(1));
            }
            average = countPrice.divide(userCount);
            UserFrequencyCountAndPriceCountDTO userFrequencyCountAndPriceCountDTO = new UserFrequencyCountAndPriceCountDTO(0, 0, 0, 0, 0, 0);
            for (UserFrequencyCountAndPriceCountEntity userFrequencyCountAndPriceCountEntity : userFrequencyCountAndPriceCountEntities) {
                BigDecimal priceCount = userFrequencyCountAndPriceCountEntity.getPriceCount();
                Integer frequencyCount = userFrequencyCountAndPriceCountEntity.getFrequencyCount();
                if (priceCount.compareTo(average) == -1) {
                    userFrequencyCountAndPriceCountDTO.setCountlessAveragePrice(userFrequencyCountAndPriceCountDTO.getCountlessAveragePrice() + 1);
                } else if (priceCount.compareTo(new BigDecimal(average.doubleValue() * 1.3)) == -1) {
                    userFrequencyCountAndPriceCountDTO.setCountOverAveragePrice(userFrequencyCountAndPriceCountDTO.getCountOverAveragePrice() + 1);
                } else {
                    userFrequencyCountAndPriceCountDTO.setCountFarAboveAveragePrice(userFrequencyCountAndPriceCountDTO.getCountFarAboveAveragePrice() + 1);
                }
                if (frequencyCount <= 5) {
                    userFrequencyCountAndPriceCountDTO.setCount0_5Frequency(userFrequencyCountAndPriceCountDTO.getCount0_5Frequency() + 1);
                } else if (frequencyCount <= 10) {
                    userFrequencyCountAndPriceCountDTO.setCount5_10Frequency(userFrequencyCountAndPriceCountDTO.getCount5_10Frequency() + 1);
                } else {
                    userFrequencyCountAndPriceCountDTO.setCountOver10Frequency(userFrequencyCountAndPriceCountDTO.getCountOver10Frequency() + 1);
                }
            }
            return userFrequencyCountAndPriceCountDTO;
        }
        return new UserFrequencyCountAndPriceCountDTO(0, 0, 0, 0, 0, 0);
    }
}
