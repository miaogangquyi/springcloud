package com.mogo.interceptor.handler;

import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import com.mogo.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


/**
 * @author miaogang
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @Description: 全局异常捕获
     * @param ApiException 捕获的异常
     * @return ResponseVo
     */
    @ExceptionHandler(value = ApiException.class)
    private ResponseVo apiExceptionHandler(ApiException e) {
        log.error("[业务异常],错误码:{},错误信息:{}",e.getCode(),e.getMsg());
        return ResponseVo.fail(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(value = Exception.class)
    private ResponseVo exceptionHandler(Exception e) {
        log.error("[服务端异常]错误信息:{}",e.toString());
        return ResponseVo.fail(ResponseEnum.SERVE_EXCEPTION);
    }
    @ExceptionHandler(value = SQLException.class)
    private ResponseVo sqlExceptionHandler(SQLException e) {
        log.error("[SQL异常],错误信息:{}",e.toString());
        return ResponseVo.fail(ResponseEnum.SQL_EXCEPTION);
    }
}
