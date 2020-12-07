
package ${package}.domain.mapstruct;

import com.mogo.domain.mapper.BaseMapper;
import ${package}.domain.entity.${className};
import ${package}.domain.dto.${className}Dto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author ${author}
* @date ${date}
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Mapper extends BaseMapper<${className}Dto, ${className}> {

}
