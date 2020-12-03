package com.mogo.exception;

import org.springframework.util.StringUtils;

/**
 * @Author: miaogang
 * @Date: 2020年12月01日
 * @Description: 字段重复异常类
 */

public class DuplicateKeyException extends RuntimeException {

    private static final long serialVersionUID = -3536868141884021251L;

    public DuplicateKeyException(Class clazz, String field, String value) {
        super(DuplicateKeyException.generateMessage(clazz.getSimpleName(), field, value));
    }

    private static String generateMessage(String entity, String field, String value) {
        return "Duplicate Key:" + StringUtils.capitalize(entity) + "." + field + ", value:" + value;

    }

}

