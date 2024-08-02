package com.linus.config.handler;

import com.linus.result.CodeEnum;
import com.linus.result.R;
import com.linus.utils.JSONUtil;
import com.linus.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        R result = R.FAIL(CodeEnum.ACCESS_DENIED);

        String resultJSON = JSONUtil.toJSON(result);
        ResponseUtils.write(response, resultJSON);
    }
}
