package com.linus.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ActivityRemarkQuery extends BaseQuery {
    private Integer id;
    private Integer activityId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String noteContent;
}
