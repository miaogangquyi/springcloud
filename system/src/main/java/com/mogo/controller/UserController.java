package com.mogo.controller;

import com.mogo.domain.User;
import com.mogo.domain.qc.UserQueryCriteria;
import com.mogo.service.UserService;
import com.mogo.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;


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

    /**
     *  用户分页查询
     * @param criteria 查询条件
     * @param pageable 分页条件
     * @return ResponseVo 分页数据
     */
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
        return ResponseVo.success(userService.login(user));
    }

    /**
     * 查询用户信息
     * @param httpServletRequest /
     * @return /
     */
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

    /**
     * 创建用户
     * @param resources /
     * @return /
     */
    @PostMapping
    public ResponseVo create(@Validated @RequestBody User resources){
        userService.create(resources);
        return ResponseVo.success();
    }

    /**
     * 修改用户
     * @param resources /
     * @return /
     */
    @PutMapping
    public ResponseVo update(@Validated(User.Update.class) @RequestBody User resources){
        userService.update(resources);
        return ResponseVo.success();
    }

    /**
     * 删除用户
     * @param ids /
     * @return /
     */
    @DeleteMapping
    public ResponseVo delete(@RequestBody Set<Long> ids){
        //for (Long id : ids) {
        //    Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        //    Integer optLevel =  Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        //    if (currentLevel > optLevel) {
        //        throw new BadRequestException("角色权限不足，不能删除：" + userService.findById(id).getUsername());
        //    }
        //}
        userService.delete(ids);
        return ResponseVo.success();
    }


}

