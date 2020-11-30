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

    SERVE_EXCEPTION(1005, "服务端异常,请联系管理员！"),

    SQL_EXCEPTION(1006, "SQL异常,请联系管理员！"),

    /**
     *  用户 2000开始
     */
    USER_NOT_LOGIN(2000,"用户未登录"),
    USER_LOGIN_ERROR(2001,"用户名或密码错误"),
    USER_USERNAME_EXISTED(2002,"当前用户名已存在"),
    USER_EMAIL_EXISTED(2003,"当前用户邮箱已存在"),



    /**
     * 角色2100开始
     */
    ROLE_NOT_EXISTED(2100,"未查询到当前角色"),
    ROLE_NAME_EXISTED(2101,"当前角色已存在"),
    ROLE_RELY_ON_USER(2102, "所选角色存在用户关联，请解除关联再试！"),



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
