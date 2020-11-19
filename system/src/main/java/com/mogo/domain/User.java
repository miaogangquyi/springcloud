package com.mogo.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: miaogang
 * @Date: 2020年11月19日
 * @Description: 用户实体类
 */
@Entity
@Getter
@Setter
@Table(name="sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = -2930296818436401360L;

    @Id
    @Column(name = "id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToMany
    //@JoinTable(name = "sys_users_roles",
    //        joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
    //        inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    //private Set<Role> roles;

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
    @CreatedBy
    @Column(name = "create_by", updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(name = "update_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    /* 分组校验 */
    public @interface Create {}

    /* 分组校验 */
    public @interface Update {}

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
