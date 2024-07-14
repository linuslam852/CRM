package com.linus.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    OK(200, "成功"),
    FAIL(500, "失敗");

    private int code;
    private String msg;
}
