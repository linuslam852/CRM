package com.linus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.linus.config.converter.*;
import lombok.Data;

/**
 * 線索表
 * t_clue
 */
@ExcelIgnoreUnannotated
@Data
public class TClue implements Serializable {

    private Integer id;

    /**
     * 線索負責人ID
     */
    @ExcelProperty("負責人")
    private Integer ownerId;

    /**
     * 活動ID
     */
    @ExcelProperty("所屬活動")
    private Integer activityId;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String fullName;

    /**
     * 稱謂
     */
    @ExcelProperty(value = "稱謂",converter = AppellationConverter.class)
    private Integer appellation;

    /**
     * 手機
     */
    @ExcelProperty("手機")
    private String phone;


    /**
     * 郵箱
     */
    @ExcelProperty("Email")
    private String email;

    /**
     * 年齡
     */
    @ExcelProperty("年齡")
    private Integer age;

    /**
     * 職業
     */
    @ExcelProperty("職業")
    private String job;

    /**
     * 年收入
     */
    @ExcelProperty("年收入")
    private BigDecimal yearIncome;

    /**
     * 地址
     */
    @ExcelProperty("地址")
    private String address;

    /**
     * 是否貸款（0不需要，1需要）
     */
    @ExcelProperty(value = "是否貸款",converter = NeedLoanConverter.class)
    private Integer needLoan;

    /**
     * 意向狀態
     */
    @ExcelProperty(value = "意向狀態",converter = IntentionStateConverter.class)
    private Integer intentionState;

    /**
     * 意向產品
     */
    @ExcelProperty(value = "意向產品",converter = IntentionProductConverter.class)
    private Integer intentionProduct;

    /**
     * 線索狀態
     */
    @ExcelProperty(value = "線索狀態",converter = StateConverter.class)
    private Integer state;

    /**
     * 線索來源
     */
    @ExcelProperty(value = "線索來源",converter = SourceConverter.class)
    private Integer source;

    /**
     * 線索描述
     */
    @ExcelProperty("線索描述")
    private String description;

    /**
     * 下次聯絡時間
     */
    private Date nextContactTime;

    /**
     * 創建時間
     */
    private Date createTime;

    /**
     * 創建人
     */
    private Integer createBy;

    /**
     * 編輯時間
     */
    private Date editTime;

    /**
     * 編輯人
     */
    private Integer editBy;

    //這裡用new是因為有些內容數值是空，查不到，所以為了防止他報錯，使用new，這樣他的字段是空，它的對象不是空。
    private TUser ownerDO = new TUser();
    private TActivity activityDO = new TActivity();
    private TDicValue appellationDO = new TDicValue();
    private TDicValue needLoanDO = new TDicValue();
    private TDicValue intentionStateDO = new TDicValue();
    private TProduct intentionProductDO = new TProduct();
    private TDicValue stateDO = new TDicValue();
    private TDicValue sourceDO = new TDicValue();


    private static final long serialVersionUID = 1L;
}