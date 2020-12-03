package com.mogo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 角色
 * @author 苗刚
 * @date 2020-11-26
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4889353714456578648L;
    @Id
    @Column(name = "role_id")
    @NotNull(groups = {Update.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    /**
     * 菜单
     */
    @ManyToMany
    @JoinTable(name = "sys_roles_menus",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "menu_id")})
    private Set<Menu> menus;

    //@ManyToMany
    //@JoinTable(name = "sys_roles_depts",
    //        joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")},
    //        inverseJoinColumns = {@JoinColumn(name = "dept_id",referencedColumnName = "dept_id")})
    //@ApiModelProperty(value = "部门", hidden = true)
    //private Set<Dept> depts;

    /**
     * 名称
     */
    @NotBlank
    private String name;


    /**
     * 级别，数值越小，级别越大
     */
    @Column(name = "level")
    private Integer level = 3;

    /**
     * 描述
     */
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
