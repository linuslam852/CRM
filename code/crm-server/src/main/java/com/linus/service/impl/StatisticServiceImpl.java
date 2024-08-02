package com.linus.service.impl;

import com.linus.manager.StatisticManager;
import com.linus.result.ChartData;
import com.linus.result.SummaryData;
import com.linus.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Resource
    private StatisticManager statisticManager;

    @Override
    public SummaryData loadSummaryData() {
        return statisticManager.loadSummaryData();
    }

    @Override
    public List<ChartData> loadSaleFunnelData() {
        return statisticManager.loadSaleFunnelData();
    }

    @Override
    public List<ChartData> loadSourcePieData() {
        return statisticManager.loadSourcePieData();
    }
}
