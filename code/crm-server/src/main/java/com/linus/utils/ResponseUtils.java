package com.linus.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtils {

    public static void write(HttpServletResponse response, String resultJSON) {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            writer.write(resultJSON);

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //spring提供的一個對象工具類
            if (!ObjectUtils.isEmpty(writer)) {
                writer.close();
            }
        }
    }
}
