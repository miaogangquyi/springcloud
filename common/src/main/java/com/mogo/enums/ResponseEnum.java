package com.mogo.enums;


import lombok.Getter;

/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 返回类型枚举
 */
@Getter
public enum ResponseEnum {


    SUCCESS(1000, "操作成功"),

    FAILED(1001, "响应失败"),

    ERROR(1002, "未知错误"),

    VALIDATE_FAILED(1003, "参数校验失败");



    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
