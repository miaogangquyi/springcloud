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

    DUPLICATE_KEY_ERROR(1006, "字段重复"),

    IS_NULL(1007, "查询为空"),

    EXTERNAL_LINKS_EXCEPTION(1008, "外链必须以http://或者https://开头"),

    NEW_CANNOT_HAVE_ID(1009, "新增时不能携带ID"),

    PERMISSION_DENIED(1010, "权限不足"),

    FILE_SIZE_1M(1011, "文件大小超过1M"),

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


    /**
     * 角色2200开始
     */
    MENU_NOT_EXISTED(2200,"未查询到当前菜单"),
    MENU_TITLE_EXISTED(2201,"当前菜单标题已存在"),
    MENU_PARENT_CANNOT_ITSELF(2202, "上级不能是自己"),

    /**
     * 代码生成
     */
    GENERATOR_EXCEPTION(2300,"代码生成错误，请联系管理员"),

    CONFIG_GENERATOR_FIRST_EXCEPTION(2301,"请先配置生成器"),

    GENERATOR_FILE_ERROR(2302,"生成失败，请手动处理已生成的文件"),

    GENERATOR_ZIP_ERROR(2303,"打包失败"),

    GENERATOR_ENV_ERROR(2304,"此环境不允许生成代码，请选择预览或者下载查看！"),

    GENERATOR_OPTION_ERROR(2305,"没有这个选项"),



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
