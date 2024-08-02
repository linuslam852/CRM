package com.linus.manager;

import com.linus.mapper.TActivityMapper;
import com.linus.mapper.TClueMapper;
import com.linus.mapper.TCustomerMapper;
import com.linus.mapper.TTranMapper;
import com.linus.result.ChartData;
import com.linus.result.SummaryData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticManager {
    @Resource
    private TActivityMapper tActivityMapper;

    @Resource
    private TClueMapper tClueMapper;

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Resource
    private TTranMapper tTranMapper;

    public SummaryData loadSummaryData(){
        //有效的市場活動總數
        Integer effectiveActivityCount = tActivityMapper.selectOnGoingActivities().size();

        //市場活動總數
         Integer totalActivityCount = tActivityMapper.selectByCount();

        //線索總數
        Integer totalClueCount = tClueMapper.selectClueByCount();

        //客戶總數
        Integer totalCustomerCount = tCustomerMapper.selectByCount();

        //成功的交易額
        BigDecimal successTranAmount = tTranMapper.selectBySuccessTranAmount();

        //交易總額（包含成功和不成功的）
        BigDecimal totalTranAmount = tTranMapper.selectByTotalTranAmount();


        return SummaryData.builder().
                effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalCustomerCount(totalCustomerCount)
                .successTranAmount(successTranAmount)
                .totalTranAmount(totalTranAmount)
                .build();
    }

    public List<ChartData> loadSaleFunnelData() {
        List<ChartData> resultList = new ArrayList<>();

        int clueCount = tClueMapper.selectClueByCount();
        int customerCount = tCustomerMapper.selectByCount();
        int tranCount = tTranMapper.selectByTotalTranCount();
        int tranSuccessCount = tTranMapper.selectBySuccessTranCount();
        ChartData clue = ChartData.builder().name("線索").value(clueCount).build();
        resultList.add(clue);

        ChartData customer = ChartData.builder().name("客戶").value(customerCount).build();
        resultList.add(customer);

        ChartData tran = ChartData.builder().name("交易").value(tranCount).build();
        resultList.add(tran);

        ChartData tranSuccess = ChartData.builder().name("成交").value(tranSuccessCount).build();
        resultList.add(tranSuccess);

        return resultList;
    }

    public List<ChartData> loadSourcePieData() {
        List<ChartData> chartDataList = tClueMapper.selectBySource();
        return chartDataList;
    }
}
