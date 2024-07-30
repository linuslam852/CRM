package com.linus.query;

import com.alibaba.excel.annotation.ExcelProperty;
import com.linus.config.converter.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ClueQuery extends BaseQuery{
    private Integer id;

    private Integer ownerId;

    private Integer activityId;

    private String fullName;

    private Integer appellation;

    private String phone;

    private String weixin;

    private String qq;

    private String email;

    private Integer age;

    private String job;

    private BigDecimal yearIncome;

    private String address;

    private Integer needLoan;

    private Integer intentionState;

    private Integer intentionProduct;

    private Integer state;

    private Integer source;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextContactTime;
}
