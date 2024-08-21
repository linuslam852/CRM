package com.linus.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 字典值表
 * t_dic_value
 */
@Data
public class TDicValue implements Serializable {

    private Integer id;

    private String typeCode;

    private String typeValue;

    private Integer order;

    private String remark;

    private static final long serialVersionUID = 1L;
}