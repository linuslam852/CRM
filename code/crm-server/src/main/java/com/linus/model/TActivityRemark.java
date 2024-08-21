package com.linus.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 市場活動備註表
 * t_activity_remark
 */
@Data
public class TActivityRemark implements Serializable {

    private Integer id;

    /**
     * 活動ID
     */
    private Integer activityId;

    /**
     * 備註內容
     */
    private String noteContent;

    /**
     * 活動備註時間
     */
    private Date createTime;

    /**
     * 備註創建人
     */
    private Integer createBy;

    /**
     * 備註編輯時間
     */
    private Date editTime;

    /**
     * 備註編輯人
     */
    private Integer editBy;

    /**
     * 刪除狀態(0正常，1刪除）
     */
    private Integer deleted;
    private TUser createDO;
    private TUser editDO;
    private static final long serialVersionUID = 1L;
}