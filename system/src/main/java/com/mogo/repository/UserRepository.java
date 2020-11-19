package com.mogo.repository;

import com.mogo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: miaogang
 * @Date: 2020年11月19日
 * @Description: 用户查询
 */

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor {
    /**
     * 根据用户名查询
     * @param username 用户名
     * @return /
     */
    User findByUsername(String username);
}
