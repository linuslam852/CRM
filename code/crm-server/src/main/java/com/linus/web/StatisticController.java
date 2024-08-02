package com.linus.web;

import com.linus.result.ChartData;
import com.linus.result.R;
import com.linus.result.SummaryData;
import com.linus.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    @GetMapping("/api/summary/data")
    public R summaryData(){
        SummaryData summaryData = statisticService.loadSummaryData();
        return R.OK(summaryData);
    }


    @GetMapping("/api/saleFunnel/data")
    public R saleFunnelData(){
        List<ChartData> chartDataList = statisticService.loadSaleFunnelData();
        return R.OK(chartDataList);
    }

    @GetMapping("/api/sourcePie/data")
    public R sourcePieData(){
        List<ChartData> chartDataList = statisticService.loadSourcePieData();
        return R.OK(chartDataList);
    }
}
