package com.linus.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 客戶表
 * t_customer
 */
@Data
public class TCustomer implements Serializable {

    private Integer id;

    private Integer clueId;

    private Integer product;

    private String description;

    private Date nextContactTime;

    private Date createTime;

    private Integer createBy;

    private Date editTime;

    private Integer editBy;

    private TClue clueDO = new TClue();
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