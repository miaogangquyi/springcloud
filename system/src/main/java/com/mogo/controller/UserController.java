package com.mogo.controller;

import com.mogo.domain.qc.UserQueryCriteria;
import com.mogo.service.UserService;
import com.mogo.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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

    private final UserService userService;

    @GetMapping("/fetchUserPage")
    public ResponseVo findUserPage(UserQueryCriteria criteria, Pageable pageable) {
        return ResponseVo.success(userService.queryPage(criteria, pageable));
    }

}

