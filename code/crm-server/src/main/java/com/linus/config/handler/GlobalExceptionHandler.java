package com.linus.config.handler;

import com.linus.result.CodeEnum;
import com.linus.result.R;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e) {
        e.printStackTrace();
        return R.FAIL(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handlerException(AccessDeniedException e) {
        e.printStackTrace();
        return R.FAIL(CodeEnum.ACCESS_DENIED);
    }

    @ExceptionHandler(DataAccessException.class)
    public R handlerSQLException(DataAccessException e) {
        e.printStackTrace();
        return R.FAIL(CodeEnum.DATA_ACCESS_EXCEPTION);
    }

}
