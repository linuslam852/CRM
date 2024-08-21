package com.linus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 市場活動表
 * t_activity
 */
@Data
public class TActivity implements Serializable {

    private Integer id;

    /**
     * 活動人ID
     */
    private Integer ownerId;

    /**
     * 活動名稱
     */
    private String name;

    /**
     * 活動開始時間
     */
    private Date startTime;

    /**
     * 活動結束時間
     */
    private Date endTime;

    /**
     * 活動預算
     */
    private BigDecimal cost;

    /**
     * 活動描述
     */
    private String description;

    /**
     * 活動創建時間
     */
    private Date createTime;

    /**
     * 活動創建人
     */
    private Integer createBy;

    /**
     * 活動編輯時間
     */
    private Date editTime;

    /**
     * 活動編輯人
     */
    private Integer editBy;

    private TUser ownerDO;
    private TUser createDO;
    private TUser editDO;

    private static final long serialVersionUID = 1L;
}