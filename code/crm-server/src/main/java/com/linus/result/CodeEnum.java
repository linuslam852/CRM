package com.linus.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    OK(200, "成功"),
    FAIL(500, "失敗"),
    TOKEN_IS_EMPTY(901, "請求Token參數為空"),
    TOKEN_IS_EXPIRED(902,"請求Token已過期"),
    TOKEN_IS_ERROR(903,"請求Token錯誤"),
    TOKEN_IS_NOT_MATCH(904,"請求Token不匹配"),
    LOGOUT_SUCCESS(200,"退出成功"),
    Data_Access_EXCEPTION(500,"數據庫操作失敗"),

    ;

    private int code;

    @NonNull
    private String msg;
    CodeEnum(String msg){
        this.msg = msg;
    }
}
