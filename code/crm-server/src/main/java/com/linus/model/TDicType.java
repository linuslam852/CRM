package com.linus.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 字典類型表
 * t_dic_type
 */
@Data
public class TDicType implements Serializable {

    private Integer id;

    private String typeCode;

    private String typeName;

    private String remark;
    private List<TDicValue> dicValueList;

    private static final long serialVersionUID = 1L;
}