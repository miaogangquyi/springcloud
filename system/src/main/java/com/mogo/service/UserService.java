package com.mogo.service;


import com.mogo.domain.User;
import com.mogo.domain.qc.UserQueryCriteria;
import com.mogo.repository.UserRepository;
import com.mogo.util.QueryHelp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author: miaogang
 * @Date: 2020年11月19日
 * @Description: 用户服务类
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<User> queryPage(UserQueryCriteria criteria, Pageable pageable) {
        return userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
    }
}

