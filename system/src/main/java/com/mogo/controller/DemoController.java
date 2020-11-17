package com.mogo.controller;

import com.mogo.enums.ResponseEnum;
import com.mogo.vo.ResponseVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: miaogang
 * @Date: 2020年11月17日
 * @Description: 测试
 */
@RestController
@RequestMapping("/test")
public class DemoController {
    @GetMapping("/suc")
    public ResponseVo test() {
        return ResponseVo.success("1");
    }
}

