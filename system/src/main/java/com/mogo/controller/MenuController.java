package com.mogo.controller;

import com.mogo.domain.dto.MenuDto;
import com.mogo.service.MenuService;
import com.mogo.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: miaogang
 * @Date: 2020年11月23日
 * @Description: 菜单
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/getMenu")
    public ResponseVo getAuthMenu(HttpServletRequest request) {
        List<MenuDto> menuDtoList = menuService.findByUser(1L);
        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
        return ResponseVo.success(menuService.buildMenus(menuDtos));
    }
}

