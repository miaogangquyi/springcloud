
package com.mogo.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author miaogang
 * @date 2020-11-23
 */
@Getter
@Setter
public class UserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4015838029635810434L;
    private Long id;

    private Set<RoleSmallDto> roles;

    private Long deptId;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

    private String avatarName;

    private String avatarPath;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    @JsonIgnore
    private Boolean isAdmin = false;

    private Date pwdResetTime;
}
