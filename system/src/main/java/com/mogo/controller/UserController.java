package com.mogo.controller;

import com.mogo.domain.User;
import com.mogo.domain.qc.UserQueryCriteria;
import com.mogo.service.UserService;
import com.mogo.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 用户请求类
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    public static final String BUSINESS_NAME = "用户";
    public static final String LOGIN_REDIS_KEY = "login.";

    private final UserService userService;
    private final RedisTemplate redisTemplate;

    @GetMapping
    public ResponseVo findUserPage(UserQueryCriteria criteria, Pageable pageable) {
        return ResponseVo.success(userService.queryPage(criteria, pageable));
    }
    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseVo login(@RequestBody User user, HttpServletRequest request) {
        log.info("用户登录开始");

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        //String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        //LOG.info("从redis中获取到的验证码：{}", imageCode);
        //if (StringUtils.isEmpty(imageCode)) {
        //    responseDto.setSuccess(false);
        //    responseDto.setMessage("验证码已过期");
        //    LOG.info("用户登录失败，验证码已过期");
        //    return responseDto;
        //}
//        if (!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())) {
//            responseDto.setSuccess(false);
//            responseDto.setMessage("验证码不对");
//            LOG.info("用户登录失败，验证码不对");
//            return responseDto;
//        } else {
//            // 验证通过后，移除验证码
//            redisTemplate.delete(userDto.getImageCodeToken());
//        }
        return ResponseVo.success(userService.login(user));
    }

    @GetMapping("/getInfo")
    public ResponseVo getInfo(HttpServletRequest httpServletRequest) {
        log.info("getInfo开始");
        // 从 http 请求头中取出 token
        return ResponseVo.success(userService.getLoginUserInfo(httpServletRequest));
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public ResponseVo logout(HttpServletRequest httpServletRequest) {
        userService.logout(httpServletRequest);
        return ResponseVo.success();
    }


}

