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
    DATA_ACCESS_EXCEPTION(500,"數據庫操作失敗"),
    ACCESS_DENIED(500,"抱歉，你沒有權限訪問"),
    WRONG_PASSWORD(500,"密碼錯誤"),
    PASSWORD_IS_EMPTY(500,"密碼為空"),
    ;

    private int code;

    @NonNull
    private String msg;
    CodeEnum(String msg){
        this.msg = msg;
    }
}
