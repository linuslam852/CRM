package com.linus.manager;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Component
public class RedisManager {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;



    public  Object getValue(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public <T>Object setValue(String key, Collection<T> data){


        Object[] t = new Object[data.size()];
        data.toArray(t);
        return redisTemplate.opsForList().leftPushAll(key,t);
    }

    public String getUniqueNumber(String key) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = localDateTime.format(formatter);

        //從redis中獲取一個自增值，這個自增值每次都是不重複的
        Long incrValue = redisTemplate.opsForValue().increment(key);
        return formattedDateTime + incrValue;
    }
}
