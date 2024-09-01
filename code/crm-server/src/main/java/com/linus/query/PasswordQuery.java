package com.linus.query;

import lombok.Data;

@Data
public class PasswordQuery extends BaseQuery{
    private Integer id;

    private String loginAct;

    private String oldPwd;

    private String newPwd;
}
