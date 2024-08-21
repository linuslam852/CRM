package com.linus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 產品表
 * t_product
 */
@Data
public class TProduct implements Serializable {

    private Integer id;

    private String name;

    private BigDecimal guidePriceS;

    private BigDecimal guidePriceE;

    private BigDecimal quotation;

    /**
     * 狀態 0在售 1賣完
     */
    private Integer state;

    private Date createTime;

    private Integer createBy;

    private Date editTime;

    private Integer editBy;

    private static final long serialVersionUID = 1L;
}