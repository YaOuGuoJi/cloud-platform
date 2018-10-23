package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.mapper.TodayTotalPriceAndFrequencyMapper;
import com.yaouguoji.platform.service.TodayPriceAndFrequencyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class TodayPriceAndFrequencyServiceImpl implements TodayPriceAndFrequencyService {
    @Resource
    private TodayTotalPriceAndFrequencyMapper todayTotalPriceAndFrequencyMapper;

    @Override
    public Map<String, BigDecimal> todayPriceAndFrequency() {
        return todayTotalPriceAndFrequencyMapper.todayTotalPriceAndFrequency();
    }
}
