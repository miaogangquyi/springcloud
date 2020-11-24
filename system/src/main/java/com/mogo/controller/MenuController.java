package com.mogo.controller;

import com.google.gson.Gson;
import com.mogo.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: miaogang
 * @Date: 2020年11月23日
 * @Description: 菜单
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {
    @GetMapping("/getMenu")
    public ResponseVo getAuthMenu(HttpServletRequest httpServletRequest) {
        log.info("getAuthMenu开始");
        String json = "[{\"name\":\"系统管理\",\"path\":\"/system\",\"hidden\":false,\"redirect\":\"noredirect\",\"component\":\"Layout\",\"alwaysShow\":true,\"meta\":{\"title\":\"系统管理\",\"icon\":\"dashboard\",\"noCache\":true},\"children\":[{\"name\":\"User\",\"path\":\"user\",\"hidden\":false,\"component\":\"system/user/index\",\"meta\":{\"title\":\"用户管理\",\"icon\":\"dashboard\",\"noCache\":true}}]}]";
        Object jsonObject = new Gson().fromJson(json, Object.class);
        log.info("菜单展示111:{}",jsonObject);
        return  ResponseVo.success(jsonObject);
    }
}

