package com.mogo.interceptor;

import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import com.mogo.service.UserService;
import com.mogo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: miaogang
 * @Date: 2020年11月04日
 * @Description: Token拦截器
 */
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private  UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String authHeader = httpServletRequest.getHeader(JwtUtil.TOKEN_HEADER);// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (authHeader == null || !authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            throw new ApiException(ResponseEnum.USER_NOT_LOGIN);
        }
        //取得token
        String token = authHeader.substring(7);
        //验证token
        boolean verify = JwtUtil.verify(token);
        if (!verify) {
            throw new ApiException(ResponseEnum.TOKEN_EXPIRED);
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}

