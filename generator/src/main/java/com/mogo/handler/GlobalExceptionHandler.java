package com.mogo.handler;

import com.mogo.exception.GeneratorException;
import com.mogo.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author miaogang
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @Description: 全局异常捕获
     * @param GeneratorException 捕获的异常
     * @return ResponseVo
     */
    @ExceptionHandler(value = GeneratorException.class)
    private ResponseVo generatorException(GeneratorException e) {
        log.error("[代码生成异常],错误码:{},错误信息:{}",e.getCode(),e.getMsg());
        e.printStackTrace();
        return ResponseVo.fail(e.getCode(),e.getMsg());
    }

}
