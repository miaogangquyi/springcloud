
package com.mogo.domain.mapstruct;

import com.mogo.domain.mapper.BaseMapper;
import com.mogo.domain.entity.Dict;
import com.mogo.domain.dto.DictDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author miaogang
* @date 2020-12-07
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictMapper extends BaseMapper<DictDto, Dict> {

}
