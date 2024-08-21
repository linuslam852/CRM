package com.linus.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TranHistoryQuery extends BaseQuery{
    private Integer id;

    private Integer tranId;

    private Integer stage;

    private BigDecimal money;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
