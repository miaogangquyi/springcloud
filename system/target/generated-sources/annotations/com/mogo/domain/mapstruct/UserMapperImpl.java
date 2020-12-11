package com.mogo.domain.mapstruct;

import com.mogo.domain.dto.RoleSmallDto;
import com.mogo.domain.dto.UserDto;
import com.mogo.domain.entity.Role;
import com.mogo.domain.entity.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-11T17:16:10+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setCreateBy( dto.getCreateBy() );
        user.setUpdatedBy( dto.getUpdatedBy() );
        user.setCreateTime( dto.getCreateTime() );
        user.setUpdateTime( dto.getUpdateTime() );
        user.setId( dto.getId() );
        user.setRoles( roleSmallDtoSetToRoleSet( dto.getRoles() ) );
        user.setUsername( dto.getUsername() );
        user.setNickName( dto.getNickName() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setAvatarName( dto.getAvatarName() );
        user.setAvatarPath( dto.getAvatarPath() );
        user.setPassword( dto.getPassword() );
        user.setEnabled( dto.getEnabled() );
        user.setIsAdmin( dto.getIsAdmin() );
        user.setPwdResetTime( dto.getPwdResetTime() );

        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setCreateBy( entity.getCreateBy() );
        userDto.setUpdatedBy( entity.getUpdatedBy() );
        userDto.setCreateTime( entity.getCreateTime() );
        userDto.setUpdateTime( entity.getUpdateTime() );
        userDto.setId( entity.getId() );
        userDto.setRoles( roleSetToRoleSmallDtoSet( entity.getRoles() ) );
        userDto.setUsername( entity.getUsername() );
        userDto.setNickName( entity.getNickName() );
        userDto.setEmail( entity.getEmail() );
        userDto.setPhone( entity.getPhone() );
        userDto.setAvatarName( entity.getAvatarName() );
        userDto.setAvatarPath( entity.getAvatarPath() );
        userDto.setPassword( entity.getPassword() );
        userDto.setEnabled( entity.getEnabled() );
        userDto.setIsAdmin( entity.getIsAdmin() );
        userDto.setPwdResetTime( entity.getPwdResetTime() );

        return userDto;
    }

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDto userDto : dtoList ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public List<UserDto> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    protected Role roleSmallDtoToRole(RoleSmallDto roleSmallDto) {
        if ( roleSmallDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleSmallDto.getId() );
        role.setName( roleSmallDto.getName() );
        role.setLevel( roleSmallDto.getLevel() );

        return role;
    }

    protected Set<Role> roleSmallDtoSetToRoleSet(Set<RoleSmallDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new HashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleSmallDto roleSmallDto : set ) {
            set1.add( roleSmallDtoToRole( roleSmallDto ) );
        }

        return set1;
    }

    protected RoleSmallDto roleToRoleSmallDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleSmallDto roleSmallDto = new RoleSmallDto();

        roleSmallDto.setId( role.getId() );
        roleSmallDto.setName( role.getName() );
        roleSmallDto.setLevel( role.getLevel() );

        return roleSmallDto;
    }

    protected Set<RoleSmallDto> roleSetToRoleSmallDtoSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleSmallDto> set1 = new HashSet<RoleSmallDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleSmallDto( role ) );
        }

        return set1;
    }
}
