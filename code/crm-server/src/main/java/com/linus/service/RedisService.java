package com.linus.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void setValue(String key,Object value);

    Object getValue(String key);

    Boolean removeValue(String key);

    void expire(String key, Long timeOut, TimeUnit timeUnit);
}
