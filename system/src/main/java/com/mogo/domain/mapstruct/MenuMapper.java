package com.mogo.domain.mapstruct;


import com.mogo.domain.Menu;
import com.mogo.domain.dto.MenuDto;
import com.mogo.domain.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author miaogang
 * @date 2020-11-17
 */

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {
}
