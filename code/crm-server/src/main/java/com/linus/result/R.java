package com.linus.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 後端接受的數據，統一用 R對象 封裝
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class R {

    //返回的狀態碼，比如200表示成功，500表示失敗
    private int code;

    //返回的狀態信息，也就是返回的那個狀態碼是代表後端什麼錯誤，比如：用戶不存在，登入密碼錯誤....
    private String msg;

    //返回的具體類型，數據可以是任何類型，比如登入成功，返回user對象
    private Object data;

    public static R OK() {
        return R.builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).build();
    }

    public static R OK(Object data) {
        return R.builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).data(data).build();
    }

    public static R OK(CodeEnum codeEnum) {
        return R.builder().code(codeEnum.getCode()).msg(codeEnum.getMsg()).build();
    }

    public static R FAIL() {
        return R.builder().code(CodeEnum.FAIL.getCode()).msg(CodeEnum.FAIL.getMsg()).build();
    }

    public static R FAIL(CodeEnum codeEnum) {
        return R.builder().code(codeEnum.getCode()).msg(codeEnum.getMsg()).build();
    }

    public static R FAIL(String msg) {
        return R.builder().code(CodeEnum.FAIL.getCode()).msg(msg).build();
    }
}
