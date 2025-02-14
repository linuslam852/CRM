package com.linus.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ClueRemarkQuery extends BaseQuery{
    private Integer clueId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String noteContent;
    private Integer noteWay;
    private Integer id;
}
