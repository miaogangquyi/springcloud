package com.mogo.util;

import cn.hutool.core.util.ObjectUtil;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import org.springframework.util.StringUtils;

/**
 * @Author: miaogang
 * @Date: 2020年12月01日
 * @Description: 校验类
 */
public class ValidationUtil {

    /**
     * 验证空
     */
    public static void isNull(Object obj, String field, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = StringUtils.capitalize(obj.getClass().getSimpleName()) + " not existed, queryParam:{" + field + ":" + value + "}";
            throw new ApiException(ResponseEnum.IS_NULL.getCode(), msg);
        }
    }
}

