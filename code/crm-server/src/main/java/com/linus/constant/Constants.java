package com.linus.constant;

public class Constants {
    public static final String LOGIN_URI = "/api/login";

    //redis的key的命名規範，項目名：模塊名：功能名：唯一業務參數(比如用戶id)
    public static final String REDIS_JWT_KEY = "crm:user:login:";

    public static final Long EXPIRE_TIME = 7 * 24 * 60 * 60L;

    public static final Long DEFAULT_EXPIRE_TIME = 30 * 60L;
}
