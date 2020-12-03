
package com.mogo.controller;

import com.mogo.domain.GenConfig;
import com.mogo.service.GenConfigService;
import com.mogo.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author miaogang
 * @date 2019-01-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/genConfig")
@Api(tags = "系统：代码生成器配置管理")
public class GenConfigController {

    private final GenConfigService genConfigService;

    @ApiOperation("查询")
    @GetMapping(value = "/{tableName}")
    public ResponseVo query(@PathVariable String tableName){
        return ResponseVo.success(genConfigService.find(tableName));
    }

    @ApiOperation("修改")
    @PutMapping
    public ResponseVo update(@Validated @RequestBody GenConfig genConfig){
        return ResponseVo.success(genConfigService.update(genConfig.getTableName(), genConfig));
    }
}
