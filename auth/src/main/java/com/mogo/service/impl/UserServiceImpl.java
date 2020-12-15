package com.mogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: miaogang
 * @Date: 2020年12月14日
 * @Description: 用户服务
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    //private List<UserDTO> userList;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

