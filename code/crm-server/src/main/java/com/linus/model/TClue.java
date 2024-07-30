package com.linus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.linus.config.converter.*;
import lombok.Data;

/**
 * 线索表
 * t_clue
 */
@ExcelIgnoreUnannotated
@Data
public class TClue implements Serializable {
    /**
     * 主键，自动增长，线索ID
     */
    private Integer id;

    /**
     * 线索所属人ID
     */
    @ExcelProperty("負責人")
    private Integer ownerId;

    /**
     * 活动ID
     */
    @ExcelProperty("所屬活動")
    private Integer activityId;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String fullName;

    /**
     * 称呼
     */
    @ExcelProperty(value = "稱謂",converter = AppellationConverter.class)
    private Integer appellation;

    /**
     * 手机号
     */
    @ExcelProperty("手機")
    private String phone;

    /**
     * 微信号
     */
    @ExcelProperty("微信")
    private String weixin;

    /**
     * QQ号
     */
    @ExcelProperty("QQ")
    private String qq;

    /**
     * 邮箱
     */
    @ExcelProperty("Email")
    private String email;

    /**
     * 年龄
     */
    @ExcelProperty("年齡")
    private Integer age;

    /**
     * 职业
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
     * 是否需要贷款（0不需要，1需要）
     */
    @ExcelProperty(value = "是否貸款",converter = NeedLoanConverter.class)
    private Integer needLoan;

    /**
     * 意向状态
     */
    @ExcelProperty(value = "意向狀態",converter = IntentionStateConverter.class)
    private Integer intentionState;

    /**
     * 意向产品
     */
    @ExcelProperty(value = "意向產品",converter = IntentionProductConverter.class)
    private Integer intentionProduct;

    /**
     * 线索状态
     */
    @ExcelProperty(value = "線索狀態",converter = StateConverter.class)
    private Integer state;

    /**
     * 线索来源
     */
    @ExcelProperty(value = "線索來源",converter = SourceConverter.class)
    private Integer source;

    /**
     * 线索描述
     */
    @ExcelProperty("線索描述")
    private String description;

    /**
     * 下次联系时间
     */
    private Date nextContactTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑人
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