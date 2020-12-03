
package com.mogo.controller;

import com.mogo.domain.ColumnInfo;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.GeneratorException;
import com.mogo.service.GenConfigService;
import com.mogo.service.GeneratorService;
import com.mogo.util.PageUtil;
import com.mogo.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author miaogang
 * @date 2019-01-02
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final GenConfigService genConfigService;

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables/all")
    public ResponseVo queryTables(){
        return ResponseVo.success(generatorService.getTables());
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables")
    public ResponseVo queryTables(@RequestParam(defaultValue = "") String name,
                                              @RequestParam(defaultValue = "0")Integer page,
                                              @RequestParam(defaultValue = "10")Integer size){
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return ResponseVo.success(generatorService.getTables(name,startEnd));
    }

    @ApiOperation("查询字段数据")
    @GetMapping(value = "/columns")
    public ResponseVo queryColumns(@RequestParam String tableName){
        List<ColumnInfo> columnInfos = generatorService.getColumns(tableName);
        return ResponseVo.success(PageUtil.toPage(columnInfos,columnInfos.size()));
    }

    @ApiOperation("保存字段数据")
    @PutMapping
    public ResponseVo save(@RequestBody List<ColumnInfo> columnInfos){
        generatorService.save(columnInfos);
        return ResponseVo.success();
    }

    @ApiOperation("同步字段数据")
    @PostMapping(value = "sync")
    public ResponseVo sync(@RequestBody List<String> tables){
        for (String table : tables) {
            generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
        }
        return ResponseVo.success();
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public ResponseVo generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response){
        if(!generatorEnabled && type == 0){
            throw new GeneratorException(ResponseEnum.GENERATOR_ENV_ERROR);
        }
        switch (type){
            // 生成代码
            case 0: generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                    break;
            // 预览
            case 1: return generatorService.preview(genConfigService.find(tableName), generatorService.getColumns(tableName));
            // 打包
            case 2: generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName), request, response);
                    break;
            default: throw new GeneratorException();
        }
        return ResponseVo.success();
    }
}
