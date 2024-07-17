package com.linus.aspect;

import com.linus.commons.DataScope;
import com.linus.model.TUser;
import com.linus.query.BaseQuery;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
@Aspect
@Component
public class DataScopeAspect {

    @Pointcut(value="@annotation(com.linus.commons.DataScope)")
    private void pointCut(){

    }

    @Around("pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataScope dataScope = methodSignature.getMethod().getDeclaredAnnotation(DataScope.class);
        String tableAlias = dataScope.tableAlias();
        String tableField = dataScope.tableField();

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        TUser tUser = JSONUtil.toBean(JWTUtils.parseJWT(token), TUser.class);
        List<String> roleList = tUser.getRoleList();

        if(!roleList.contains("admin")){
            Object params = joinPoint.getArgs()[0];
            if(params instanceof BaseQuery){
                BaseQuery query = (BaseQuery) params;

                //拼接
                //select * from t_user tu where tu.id = 2
                query.setFilterSQL(" and " + tableAlias + "." + tableField + " = " + tUser.getId());
            }
        }
        Object result = joinPoint.proceed();
        return result;
    }
}
