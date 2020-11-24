package com.mogo.enums;


import lombok.Getter;

/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 返回类型枚举
 */
@Getter
public enum ResponseEnum {

    /**
     * 成功
     */
    SUCCESS(1000, "操作成功"),

    FAILED(1001, "响应失败"),

    VALIDATE_FAILED(1003, "参数校验失败"),

    TOKEN_EXPIRED(1004,"Token已过期，请重新登录"),

    USER_NOT_LOGIN(1005,"用户未登录"),

    USER_LOGIN_ERROR(1006,"用户名或密码错误"),

    ERROR(5000, "未知错误");
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
