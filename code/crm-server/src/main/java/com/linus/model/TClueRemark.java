package com.linus.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 線索備註表
 * t_clue_remark
 */
@Data
public class TClueRemark implements Serializable {

    private Integer id;

    private Integer clueId;

    private Integer noteWay;

    private String noteContent;

    private Date createTime;

    private Integer createBy;

    private Date editTime;

    private Integer editBy;

    private Integer deleted;
    private TUser createDO;
    private TUser editDO;
    private TDicValue noteWayDO;

    private static final long serialVersionUID = 1L;
}