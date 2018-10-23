package com.yaouguoji.platform.service;

import java.math.BigDecimal;
import java.util.Map;

public interface TodayPriceAndFrequencyService {
    Map<String, BigDecimal> todayPriceAndFrequency();
}
