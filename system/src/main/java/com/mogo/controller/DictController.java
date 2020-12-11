
package com.mogo.controller;
import com.mogo.domain.entity.Dict;
import com.mogo.domain.qc.DictQueryCriteria;
import com.mogo.service.DictService;
import com.mogo.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author miaogang
* @date 2020-12-07
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "字典管理")
@RequestMapping("/dict")
public class DictController {

    private final DictService dictService;

    //@ApiOperation("导出数据")
    //@GetMapping(value = "/download")
    //public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
    //}

    @GetMapping
    @ApiOperation("查询字典")
    public ResponseVo query(DictQueryCriteria criteria, Pageable pageable){
        return ResponseVo.success(dictService.queryAll(criteria,pageable));
    }

    @PostMapping
    @ApiOperation("新增字典")
    public ResponseVo create(@Validated @RequestBody Dict resources){
        return ResponseVo.success(dictService.create(resources));
    }

    @PutMapping
    @ApiOperation("修改字典")
    public ResponseVo update(@Validated @RequestBody Dict resources){
        dictService.update(resources);
        return ResponseVo.success();
    }

    @ApiOperation("删除字典")
    @DeleteMapping
    public ResponseVo delete(@RequestBody Long[] ids) {
        dictService.deleteAll(ids);
        return ResponseVo.success();
    }
}
