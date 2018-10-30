package com.yaouguoji.platform.impl;

import com.yaouguoji.platform.mapper.NowTotalPeopleMapper;
import com.yaouguoji.platform.service.NowTotalPeopleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class NowTotalPeopleServiceImpl implements NowTotalPeopleService {
    @Resource
    private NowTotalPeopleMapper nowTotalPeopleMapper;

    @Override
    public Map<String, Integer> todayTotalPeople() {
        return nowTotalPeopleMapper.totalPeople();
    }
}
