package com.linus.config.handler;

import com.linus.constant.Constants;
import com.linus.model.TUser;
import com.linus.result.CodeEnum;
import com.linus.result.R;
import com.linus.service.RedisService;
import com.linus.utils.JSONUtil;
import com.linus.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TUser tUser = (TUser)authentication.getPrincipal();
        //刪除redis
        redisService.removeValue(Constants.REDIS_JWT_KEY + tUser.getId());

        R result = R.OK(CodeEnum.LOGOUT_SUCCESS);
        String resultJSON = JSONUtil.toJSON(result);
        ResponseUtils.write(response,resultJSON);

    }
}
