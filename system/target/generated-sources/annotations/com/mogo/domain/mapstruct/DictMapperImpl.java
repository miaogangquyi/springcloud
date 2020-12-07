package com.mogo.domain.mapstruct;

import com.mogo.domain.dto.DictDto;
import com.mogo.domain.entity.Dict;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-07T15:33:57+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class DictMapperImpl implements DictMapper {

    @Override
    public Dict toEntity(DictDto dto) {
        if ( dto == null ) {
            return null;
        }

        Dict dict = new Dict();

        dict.setDictId( dto.getDictId() );
        dict.setName( dto.getName() );
        dict.setDescription( dto.getDescription() );
        dict.setCreateBy( dto.getCreateBy() );
        dict.setUpdateBy( dto.getUpdateBy() );
        dict.setCreateTime( dto.getCreateTime() );
        dict.setUpdateTime( dto.getUpdateTime() );

        return dict;
    }

    @Override
    public DictDto toDto(Dict entity) {
        if ( entity == null ) {
            return null;
        }

        DictDto dictDto = new DictDto();

        dictDto.setDictId( entity.getDictId() );
        dictDto.setName( entity.getName() );
        dictDto.setDescription( entity.getDescription() );
        dictDto.setCreateBy( entity.getCreateBy() );
        dictDto.setUpdateBy( entity.getUpdateBy() );
        dictDto.setCreateTime( entity.getCreateTime() );
        dictDto.setUpdateTime( entity.getUpdateTime() );

        return dictDto;
    }

    @Override
    public List<Dict> toEntity(List<DictDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dict> list = new ArrayList<Dict>( dtoList.size() );
        for ( DictDto dictDto : dtoList ) {
            list.add( toEntity( dictDto ) );
        }

        return list;
    }

    @Override
    public List<DictDto> toDto(List<Dict> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictDto> list = new ArrayList<DictDto>( entityList.size() );
        for ( Dict dict : entityList ) {
            list.add( toDto( dict ) );
        }

        return list;
    }
}
