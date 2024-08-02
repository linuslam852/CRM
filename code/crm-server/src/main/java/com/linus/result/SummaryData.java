package com.linus.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SummaryData {
    //有效的市場活動總數
    private Integer effectiveActivityCount;

    //市場活動總數
    private Integer totalActivityCount;

    //線索總數
    private Integer totalClueCount;

    //客戶總數
    private Integer totalCustomerCount;

    //成功的交易額
    private BigDecimal successTranAmount;

    //交易總額（包含成功和不成功的）
    private BigDecimal totalTranAmount;

}
