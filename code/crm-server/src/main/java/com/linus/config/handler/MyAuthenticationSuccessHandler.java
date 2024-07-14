package com.linus.config.handler;

import com.linus.constant.Constants;
import com.linus.model.TUser;
import com.linus.result.R;
import com.linus.service.RedisService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import com.linus.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TUser tUser = (TUser) authentication.getPrincipal();

        String userJSON = JSONUtil.toJSON(tUser);
        //生成JWT
        String jwt = JWTUtils.createJWT(userJSON);
        //寫入Redis
        redisService.setValue(Constants.REDIS_JWT_KEY + tUser.getId(), jwt);

        String rememberMe = request.getParameter("rememberMe");
        if(Boolean.parseBoolean(rememberMe)){
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
        }else{
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        R result = R.OK(jwt);

        String resultJSON = JSONUtil.toJSON(result);

        ResponseUtils.write(response, resultJSON);

    }
}
