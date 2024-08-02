package com.linus.result;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerExcel {


    @ExcelProperty(value = "所屬人")
    private String ownerName;

    @ExcelProperty(value = "所属活動")
    private String activityName;

    @ExcelProperty(value = "客戶姓名")
    private String fullName;

    @ExcelProperty(value = "客户稱謂")
    private String appellationName;

    @ExcelProperty(value = "客户手機")
    private String phone;

    @ExcelProperty(value = "客户郵箱")
    private String email;

    @ExcelProperty(value = "客户年齡")
    private int age;

    @ExcelProperty(value = "客户職業")
    private String job;

    @ExcelProperty(value = "客户年收入")
    private BigDecimal yearIncome;

    @ExcelProperty(value = "客户住址")
    private String address;

    @ExcelProperty(value = "是否貸款")
    private String needLoadName;

    @ExcelProperty(value = "客户產品")
    private String productName;

    @ExcelProperty(value = "客户來源")
    private String sourceName;

    @ExcelProperty(value = "客户描述")
    private String description;

    @ExcelProperty(value = "下次聯絡時間")
    private Date nextContactTime;

}
