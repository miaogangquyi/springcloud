package com.mogo.service;


import com.mogo.domain.User;
import com.mogo.domain.qc.UserQueryCriteria;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import com.mogo.repository.UserRepository;
import com.mogo.util.JwtUtil;
import com.mogo.util.QueryHelp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: miaogang
 * @Date: 2020年11月19日
 * @Description: 用户服务类
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public Page<User> queryPage(UserQueryCriteria criteria, Pageable pageable) {
        return userRepository.findAll((root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
    }

    /**
     * 用户登录
     * @param user 用户信息
     * @return token
     */
    public String login(User user) {
        User userInfo = this.userRepository.findByUsername(user.getUsername());
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("[用户登录]用户登录失败，没有查询到当前用户:{}", user.getUsername());
            throw new ApiException(ResponseEnum.USER_LOGIN_ERROR);
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if (!user.getPassword().equals(userInfo.getPassword())) {
            log.error("[用户登录]用户登录失败，当前登录用户：{},登录密码错误", user.getUsername());
            throw new ApiException(ResponseEnum.USER_LOGIN_ERROR);
        }
        //redisTemplate.opsForValue().set(LOGIN_REDIS_KEY+loginUserDto.getId(), JSON.toJSONString(loginUserDto), 3600, TimeUnit.SECONDS);
        return JwtUtil.sign(userInfo.getUsername(), String.valueOf(userInfo.getId()));
    }

    /**
     * 获得登录用户信息
     * @param token 请求token
     * @return 用户信息
     */
    public User getLoginUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
        // 如果不是映射到方法直接通过
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            throw new ApiException(ResponseEnum.USER_NOT_LOGIN);
        }
        //取得token
        String token = authHeader.substring(7);
        String username = JwtUtil.getUsername(token);
        return userRepository.findByUsername(username);
    }

    /**
     * 登出
     * @param Token 请求Token
     */
    public void logout(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        String authHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
        // 如果不是映射到方法直接通过
        if (authHeader == null || !authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            throw new ApiException(ResponseEnum.USER_NOT_LOGIN);
        }
        //取得token
        String token = authHeader.substring(7);
        String userId = JwtUtil.getUserId(token);
        //redisTemplate.delete(LOGIN_REDIS_KEY+userId);
        //LOG.info("从redis中删除token:{}", token);
    }


}

