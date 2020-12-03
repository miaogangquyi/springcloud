package com.mogo.exception;

import com.mogo.enums.ResponseEnum;
import lombok.Getter;

/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 请求异常
 */
@Getter
public class GeneratorException extends RuntimeException {
    private static final long serialVersionUID = 1960421775401041020L;

    private int code;
    private String msg;

    public GeneratorException() {
        this(1011, "响应失败");
    }

    public GeneratorException(String msg) {
        this(1001, msg);
    }

    public GeneratorException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public GeneratorException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }
}
