
package ${package}.controller;
import ${package}.domain.entity.${className};
import ${package}.service.${className}Service;
import ${package}.domain.qc.${className}QueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import com.mogo.vo.ResponseVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author ${author}
* @date ${date}
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "${apiAlias}管理")
@RequestMapping("/${changeClassName}")
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, ${className}QueryCriteria criteria) throws IOException {
<#--        ${changeClassName}Service.download(${changeClassName}Service.queryAll(criteria), response);-->
    }

    @GetMapping
    @ApiOperation("查询${apiAlias}")
    public ResponseVo query(${className}QueryCriteria criteria, Pageable pageable){
        return ResponseVo.success(${changeClassName}Service.queryAll(criteria,pageable));
    }

    @PostMapping
    @ApiOperation("新增${apiAlias}")
    public ResponseVo create(@Validated @RequestBody ${className} resources){
        return ResponseVo.success(${changeClassName}Service.create(resources));
    }

    @PutMapping
    @ApiOperation("修改${apiAlias}")
    public ResponseVo update(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.update(resources);
        return ResponseVo.success();
    }

    @ApiOperation("删除${apiAlias}")
    @DeleteMapping
    public ResponseVo delete(@RequestBody ${pkColumnType}[] ids) {
        ${changeClassName}Service.deleteAll(ids);
        return ResponseVo.success();
    }
}
