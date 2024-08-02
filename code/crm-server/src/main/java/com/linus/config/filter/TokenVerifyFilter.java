package com.linus.config.filter;

import com.linus.constant.Constants;
import com.linus.model.TUser;
import com.linus.result.CodeEnum;
import com.linus.result.R;
import com.linus.service.RedisService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import com.linus.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class TokenVerifyFilter extends OncePerRequestFilter {

    @Resource
    private RedisService redisService;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(Constants.LOGIN_URI)){//如果是登入請求，此時還沒生成jwt，則不需要對登入請求進行jwt驗證
            //驗證jwt通過了，讓filter繼續執行
            filterChain.doFilter(request,response);
        } else {
            String token = null;
            if(request.getRequestURI().equals(Constants.EXPORTEXCEL_URI)){
                token = request.getParameter("Authorization");
            } else {
                token = request.getHeader("Authorization");
            }

            if (!StringUtils.hasText(token)){
                R result = R.FAIL(CodeEnum.TOKEN_IS_EMPTY);

                String resultJSON = JSONUtil.toJSON(result);

                ResponseUtils.write(response,resultJSON);

                return;
            }

            if(!JWTUtils.verifyJWT(token)){
                R result = R.FAIL(CodeEnum.TOKEN_IS_ERROR);
                String resultJSON = JSONUtil.toJSON(result);
                ResponseUtils.write(response,resultJSON);
                return;
            }

            String userJSON= JWTUtils.parseJWT(token);
            TUser tUser = JSONUtil.toBean(userJSON, TUser.class);
            String redisToken = (String) redisService.getValue(Constants.REDIS_JWT_KEY + tUser.getId());

            if(!StringUtils.hasText(redisToken)){
                R result = R.FAIL(CodeEnum.TOKEN_IS_EXPIRED);

                String resultJSON = JSONUtil.toJSON(result);
                ResponseUtils.write(response,resultJSON);
                return;
            }

            if(!token.equals(redisToken)){
                R result = R.FAIL(CodeEnum.TOKEN_IS_NOT_MATCH);
                String resultJSON = JSONUtil.toJSON(result);
                ResponseUtils.write(response,resultJSON);
                return;
            }

            //jwt驗證通過了，配置spring security上下文，設置當前用戶已登入，後續不再攔截
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tUser,tUser.getLoginPwd(),tUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //異步刷新token
            threadPoolTaskExecutor.execute(()->{
                String rememberMe = request.getHeader("rememberMe");
                if(Boolean.parseBoolean(rememberMe)){
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
                }else{
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                }
            });

            //驗證jwt通過，讓Filter繼續執行
            filterChain.doFilter(request,response);
        }
    }
}
