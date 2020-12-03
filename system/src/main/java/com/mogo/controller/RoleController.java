package com.mogo.controller;

import cn.hutool.core.lang.Dict;
import com.mogo.domain.dto.RoleDto;
import com.mogo.domain.dto.RoleSmallDto;
import com.mogo.domain.entity.Role;
import com.mogo.domain.qc.RoleQueryCriteria;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import com.mogo.service.RoleService;
import com.mogo.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：角色管理")
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    private static final String ENTITY_NAME = "role";

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    public ResponseVo query(@PathVariable Long id){
        return ResponseVo.success(roleService.findById(id));
    }

    //@ApiOperation("导出角色数据")
    //@GetMapping(value = "/download")
    //public void download(HttpServletResponse response, RoleQueryCriteria criteria) throws IOException {
    //    roleService.download(roleService.queryAll(criteria), response);
    //}

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    public ResponseVo query(){
        return ResponseVo.success(roleService.queryAll());
    }

    @ApiOperation("查询角色")
    @GetMapping
    public ResponseVo query(RoleQueryCriteria criteria, Pageable pageable){
        return ResponseVo.success(roleService.queryAll(criteria,pageable));
    }

    @ApiOperation("获取用户级别")
    @GetMapping(value = "/level")
    public ResponseVo getLevel(){
        return ResponseVo.success(Dict.create().set("level", getLevels(null)));
    }

    @ApiOperation("新增角色")
    @PostMapping
    public ResponseVo create(@Validated @RequestBody Role resources){
        if (resources.getId() != null) {
            throw new ApiException(ResponseEnum.NEW_CANNOT_HAVE_ID);
        }
        getLevels(resources.getLevel());
        roleService.create(resources);
        return ResponseVo.success(HttpStatus.CREATED);
    }

    @ApiOperation("修改角色")
    @PutMapping
    public ResponseVo update(@Validated(Role.Update.class) @RequestBody Role resources){
        getLevels(resources.getLevel());
        roleService.update(resources);
        return ResponseVo.success(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/menu")
    public ResponseVo updateMenu(@RequestBody Role resources){
        RoleDto role = roleService.findById(resources.getId());
        getLevels(role.getLevel());
        roleService.updateMenu(resources,role);
        return ResponseVo.success(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public ResponseVo delete(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            RoleDto role = roleService.findById(id);
            getLevels(role.getLevel());
        }
        // 验证是否被用户关联
        roleService.verification(ids);
        roleService.delete(ids);
        return ResponseVo.success(HttpStatus.OK);
    }

    /**
     * 获取用户的角色级别
     * @return /
     */
    private int getLevels(Integer level){
        //List<Integer> levels = roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList());

        List<Integer> levels = roleService.findByUsersId(1L).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList());
        int min = Collections.min(levels);
        if(level != null){
            if(level < min){
                throw new ApiException(ResponseEnum.PERMISSION_DENIED);
            }
        }
        return min;
    }
}
