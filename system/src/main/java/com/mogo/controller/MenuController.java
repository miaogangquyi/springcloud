package com.mogo.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mogo.domain.dto.MenuDto;
import com.mogo.domain.entity.Menu;
import com.mogo.domain.mapstruct.MenuMapper;
import com.mogo.domain.qc.MenuQueryCriteria;
import com.mogo.service.MenuService;
import com.mogo.util.PageUtil;
import com.mogo.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    private final MenuMapper menuMapper;

    //@ApiOperation("导出菜单数据")
    //@GetMapping(value = "/download")
    //public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws Exception {
    //    menuService.download(menuService.queryAll(criteria, false), response);
    //}

    @GetMapping(value = "/build")
    @ApiOperation("获取前端所需菜单")
    public ResponseVo buildMenus(){
        List<MenuDto> menuDtoList = menuService.findByUser(1L);
        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
        return ResponseVo.success(menuService.buildMenus(menuDtos));
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/lazy")
    public ResponseVo query(@RequestParam Long pid){
        return ResponseVo.success(menuService.getMenus(pid));
    }

    @ApiOperation("根据菜单ID返回所有子节点ID，包含自身ID")
    @GetMapping(value = "/child")
    //@PreAuthorize("@el.check('menu:list','roles:list')")
    public ResponseVo child(@RequestParam Long id){
        Set<Menu> menuSet = new HashSet<>();
        List<MenuDto> menuList = menuService.getMenus(id);
        menuSet.add(menuService.findOne(id));
        menuSet = menuService.getChildMenus(menuMapper.toEntity(menuList), menuSet);
        Set<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toSet());
        return ResponseVo.success(ids);
    }

    @GetMapping
    @ApiOperation("查询菜单")
    //@PreAuthorize("@el.check('menu:list')")
    public ResponseVo query(MenuQueryCriteria criteria) throws Exception {
        List<MenuDto> menuDtoList = menuService.queryAll(criteria, true);
        return ResponseVo.success(PageUtil.toPage(menuDtoList, menuDtoList.size()));
    }

    @ApiOperation("查询菜单:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    //@PreAuthorize("@el.check('menu:list')")
    public ResponseVo getSuperior(@RequestBody List<Long> ids) {
        Set<MenuDto> menuDtos = new LinkedHashSet<>();
        if(CollectionUtil.isNotEmpty(ids)){
            for (Long id : ids) {
                MenuDto menuDto = menuService.findById(id);
                menuDtos.addAll(menuService.getSuperior(menuDto, new ArrayList<>()));
            }
            return ResponseVo.success(menuService.buildTree(new ArrayList<>(menuDtos)));
        }
        return ResponseVo.success(menuService.getMenus(null));
    }

    @ApiOperation("新增菜单")
    @PostMapping
    public ResponseVo create(@Validated @RequestBody Menu resources){
        //if (resources.getId() != null) {
        //    throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        //}
        menuService.create(resources);
        return ResponseVo.success();
    }

    @ApiOperation("修改菜单")
    @PutMapping
    public ResponseVo update(@Validated(Menu.Update.class) @RequestBody Menu resources){
        menuService.update(resources);
        return ResponseVo.success();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping
    public ResponseVo delete(@RequestBody Set<Long> ids){
        Set<Menu> menuSet = new HashSet<>();
        for (Long id : ids) {
            List<MenuDto> menuList = menuService.getMenus(id);
            menuSet.add(menuService.findOne(id));
            menuSet = menuService.getChildMenus(menuMapper.toEntity(menuList), menuSet);
        }
        menuService.delete(menuSet);
        return ResponseVo.success();
    }

    //private List<Menu> convList(List<MenuDto> dtoList) {
    //
    //}

}

