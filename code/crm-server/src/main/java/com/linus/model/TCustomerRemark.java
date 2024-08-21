package com.linus.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 客戶備註表
 * t_customer_remark
 */
@Data
public class TCustomerRemark implements Serializable {

    private Integer id;

    private Integer customerId;

    private Integer noteWay;

    private String noteContent;

    private Integer createBy;

    private Date createTime;

    private Date editTime;

    private Integer editBy;

    private Integer deleted;

    private TUser createDO;
    private TUser editDO;
    private TDicValue noteWayDO;

    private static final long serialVersionUID = 1L;
}