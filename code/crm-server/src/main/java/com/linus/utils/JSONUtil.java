package com.linus.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJSON(Object object){
        try{
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> clazz){
        try{
            return OBJECT_MAPPER.readValue(json,clazz);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
