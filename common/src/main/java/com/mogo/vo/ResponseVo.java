package com.mogo.vo;

import com.mogo.enums.ResponseEnum;
import lombok.Data;

/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 统一返回参数
 */
@Data
public class ResponseVo<T> {
    private Integer code;
    private String msg;
    private T data;

    // 全参构造方法
    public ResponseVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // ResponseEnum枚举作为参数的构造方法
    public ResponseVo(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.data = data;
    }

    // 返回成功，有返回值
    public static<T> ResponseVo success(T data) {
        return new ResponseVo(ResponseEnum.SUCCESS, data);
    }

    // 返回成功，无返回值
    public static ResponseVo success() {
        return success(null);
    }

    // 返回失败
    public static ResponseVo fail(Integer code, String msg) {
        return new ResponseVo(ResponseEnum.FAILED,null);
    }



}

