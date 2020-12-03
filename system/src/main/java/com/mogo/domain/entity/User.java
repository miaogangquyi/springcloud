package com.mogo.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: miaogang
 * @Date: 2020年11月19日
 * @Description: 用户实体类
 */
@Entity
@Getter
@Setter
@Table(name="sys_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2930296818436401360L;

    @Id
    @Column(name = "user_id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "sys_users_roles",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    private Set<Role> roles;

    //@ManyToMany
    //@JoinTable(name = "sys_users_jobs",
    //        joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
    //        inverseJoinColumns = {@JoinColumn(name = "job_id",referencedColumnName = "job_id")})
    //private Set<Job> jobs;

    //@OneToOne
    //@JoinColumn(name = "dept_id")
    //@ApiModelProperty(value = "用户部门")
    //private Dept dept;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String nickName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private String sex;

    private String avatarName;

    private String avatarPath;

    private String password;

    @NotNull
    private Boolean enabled;

    private Boolean isAdmin = false;

    @Column(name = "pwd_reset_time")
    private Date pwdResetTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
