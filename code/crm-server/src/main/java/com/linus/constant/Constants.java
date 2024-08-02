package com.linus.constant;

public class Constants {
    public static final String LOGIN_URI = "/api/login";

    //redis的key的命名規範，項目名：模塊名：功能名：唯一業務參數(比如用戶id)
    public static final String REDIS_JWT_KEY = "crm:user:login:";
    public static final String REDIS_OWNER_KEY = "crm:user:owner";

    public static final Long EXPIRE_TIME = 7 * 24 * 60 * 60L;

    public static final Long DEFAULT_EXPIRE_TIME = 30 * 60L;

    //分頁時每頁顯示10條
    public static final int PAGE_SIZE = 10;

    public static final String EMPTY = "";

    public static final String EXCEL_FILE_NAME = "客戶信息數據";

    public static final String EXPORTEXCEL_URI = "/api/exportExcel";
}
