package com.mogo.exception;

import com.mogo.enums.ResponseEnum;
import lombok.Getter;

/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 请求异常
 */
@Getter
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1960421775401041020L;

    private int code;
    private String msg;

    public ApiException() {
        this(1001, "响应失败");
    }

    public ApiException(String msg) {
        this(1001, msg);
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public ApiException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }
}
