
package ${package}.service;

import ${package}.domain.entity.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
                import com.mogo.exception.DuplicateKeyException;
            </#if>
        </#if>
    </#list>
</#if>
import com.mogo.util.ValidationUtil;
<#--import com.mogo.util.FileUtil;-->
import lombok.RequiredArgsConstructor;
import ${package}.repository.${className}Repository;
import ${package}.service.${className}Service;
import ${package}.domain.dto.${className}Dto;
import ${package}.domain.qc.${className}QueryCriteria;
import ${package}.domain.mapstruct.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#if !auto && pkColumnType = 'Long'>
    import cn.hutool.core.lang.Snowflake;
    import cn.hutool.core.util.IdUtil;
</#if>
<#if !auto && pkColumnType = 'String'>
    import cn.hutool.core.util.IdUtil;
</#if>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.mogo.util.PageUtil;
import com.mogo.util.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @description 服务实现
* @author ${author}
* @date ${date}
**/
@Service
@RequiredArgsConstructor
public class ${className}Service {

private final ${className}Repository ${changeClassName}Repository;
private final ${className}Mapper ${changeClassName}Mapper;

public Map<String,Object> queryAll(${className}QueryCriteria criteria, Pageable pageable){
Page<${className}> page = ${changeClassName}Repository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
return PageUtil.toPage(page.map(${changeClassName}Mapper::toDto));
}

public List<${className}Dto> queryAll(${className}QueryCriteria criteria){
    return ${changeClassName}Mapper.toDto(${changeClassName}Repository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Transactional
    public ${className}Dto findById(${pkColumnType} ${pkChangeColName}) {
    ${className} ${changeClassName} = ${changeClassName}Repository.findById(${pkChangeColName}).orElseGet(${className}::new);
    ValidationUtil.isNull(${changeClassName},"${pkChangeColName}",${pkChangeColName});
    return ${changeClassName}Mapper.toDto(${changeClassName});
    }

    @Transactional(rollbackFor = Exception.class)
    public ${className}Dto create(${className} resources) {
    <#if !auto && pkColumnType = 'Long'>
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.set${pkCapitalColName}(snowflake.nextId());
    </#if>
    <#if !auto && pkColumnType = 'String'>
        resources.set${pkCapitalColName}(IdUtil.simpleUUID());
    </#if>
    <#if columns??>
        <#list columns as column>
            <#if column.columnKey = 'UNI'>
                if(${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}()) != null){
                throw new DuplicateKeyException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
                }
            </#if>
        </#list>
    </#if>
    return ${changeClassName}Mapper.toDto(${changeClassName}Repository.save(resources));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
    ${className} ${changeClassName} = ${changeClassName}Repository.findById(resources.get${pkCapitalColName}()).orElseGet(${className}::new);
    ValidationUtil.isNull( ${changeClassName},"${pkChangeColName}",resources.get${pkCapitalColName}());
    <#if columns??>
        <#list columns as column>
            <#if column.columnKey = 'UNI'>
                <#if column_index = 1>
                    ${className} ${changeClassName}1 = null;
                </#if>
                ${changeClassName}1 = ${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}());
                if(${changeClassName}1 != null && !${changeClassName}1.get${pkCapitalColName}().equals(${changeClassName}.get${pkCapitalColName}())){
                throw new DuplicateKeyException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
                }
            </#if>
        </#list>
    </#if>
    ${changeClassName}.copy(resources);
    ${changeClassName}Repository.save(${changeClassName});
    }

    public void deleteAll(${pkColumnType}[] ids) {
    for (${pkColumnType} ${pkChangeColName} : ids) {
    ${changeClassName}Repository.deleteById(${pkChangeColName});
    }
    }

<#--    public void download(List<${className}Dto> all, HttpServletResponse response) throws IOException {-->
<#--        List<Map<String, Object>> list = new ArrayList<>();-->
<#--        for (${className}Dto ${changeClassName} : all) {-->
<#--        Map<String,Object> map = new LinkedHashMap<>();-->
<#--        <#list columns as column>-->
<#--            <#if column.columnKey != 'PRI'>-->
<#--                <#if column.remark != ''>-->
<#--                    map.put("${column.remark}", ${changeClassName}.get${column.capitalColumnName}());-->
<#--                <#else>-->
<#--                    map.put(" ${column.changeColumnName}",  ${changeClassName}.get${column.capitalColumnName}());-->
<#--                </#if>-->
<#--            </#if>-->
<#--        </#list>-->
<#--        list.add(map);-->
<#--        }-->
<#--        FileUtil.downloadExcel(list, response);-->
<#--        }-->
    }
