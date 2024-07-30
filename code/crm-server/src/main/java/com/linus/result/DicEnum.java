package com.linus.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

public enum DicEnum {
    APPELLATION("appellation"),
    SOURCE("source"),
    STATE("clueState"),
    INTENTIONSTATE("intentionState"),
    PRODUCT("product"),
    NEEDLOAN("needLoan"),

    ACTIVITY("activity"),
    ;
    @Setter
    @Getter
    private String code;
}
