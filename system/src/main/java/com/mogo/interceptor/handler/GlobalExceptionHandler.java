package com.mogo.interceptor.handler;

import com.mogo.exception.ApiException;
import com.mogo.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //@ExceptionHandler(value = ValidatorException.class)
    //@ResponseBody
    //public ResultVO validatorExceptionHandler(ValidatorException e) {
    //    ResultVO resultVO = new ResultVO();
    //    resultVO.setCode(-1);
    //    LOG.warn(e.getMessage());
    //    resultVO.setMsg("请求参数异常！");
    //    return resultVO;
    //}
    @ExceptionHandler(value = ApiException.class)
    private ResponseVo apiExceptionHandler(ApiException e) {
        return ResponseVo.fail(e.getCode(),e.getMsg());
    }
}
