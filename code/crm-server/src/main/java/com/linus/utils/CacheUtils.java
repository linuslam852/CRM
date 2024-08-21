package com.linus.utils;

import org.springframework.util.ObjectUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CacheUtils {

    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> dbSelector, Consumer<T> cacheSave){
        //從redis查詢
        T t = cacheSelector.get();
        //如果redis沒查到
        if(ObjectUtils.isEmpty(t)){
            //從數據庫查
            T db = dbSelector.get();
            //數據庫查到了數據
            if(!ObjectUtils.isEmpty(db)){
                //將數據放入redis
                cacheSave.accept(db);
            }
            return db;
        } else {
            return t;
        }
    }
}
