package com.linus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.util.HashMap;
import java.util.Map;


@MapperScan(basePackages = {"com.linus.mapper"})
@SpringBootApplication
public class CrmServerApplication implements CommandLineRunner {

    public static final Map<String,Object> cacheMap = new HashMap<>();

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    public static void main(String[] args) {
        SpringApplication.run(CrmServerApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //由4行固定的代碼，設置value采用json序列化
        //設置value的序列化方式使用json的序列化方式（默认情况下是jdk序列化）
        ObjectMapper objectMapper = new ObjectMapper();
        //設置對象里面的字段的可見性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //設置多態行為
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING);
        //創建jackson序列化器
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(objectMapper, Object.class);

        //對值的序列化(采用的是json序列化)
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
