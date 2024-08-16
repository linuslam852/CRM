package com.linus.query;

import lombok.Data;

@Data
public class CustomerRemarkQuery extends BaseQuery{
    private Integer customerId;
    private String noteContent;
    private Integer noteWay;
    private Integer id;
}