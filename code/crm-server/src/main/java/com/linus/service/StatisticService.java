package com.linus.service;

import com.linus.result.ChartData;
import com.linus.result.SummaryData;

import java.util.List;

public interface StatisticService {
    SummaryData loadSummaryData();

    List<ChartData> loadSaleFunnelData();

    List<ChartData> loadSourcePieData();
}
