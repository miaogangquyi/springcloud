package com.mogo.service;


import com.mogo.domain.entity.User;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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
        Long userId = JwtUtil.getUserId(token);
        //redisTemplate.delete(LOGIN_REDIS_KEY+userId);
        //LOG.info("从redis中删除token:{}", token);
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        if (userRepository.findByUsername(resources.getUsername()) != null) {
            throw new ApiException(ResponseEnum.USER_USERNAME_EXISTED);
        }
        if (userRepository.findByEmail(resources.getEmail()) != null) {
            throw new ApiException(ResponseEnum.USER_EMAIL_EXISTED);
        }
        // 默认密码 123456
        resources.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        userRepository.save(resources);
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        User user1 = userRepository.findByUsername(resources.getUsername());
        User user2 = userRepository.findByEmail(resources.getEmail());

        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new ApiException(ResponseEnum.USER_USERNAME_EXISTED);        }

        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new ApiException(ResponseEnum.USER_EMAIL_EXISTED);
        }
        // 如果用户的角色改变
        //if (!resources.getRoles().equals(user.getRoles())) {
        //    redisUtils.del(CacheKey.DATE_USER + resources.getId());
        //    redisUtils.del(CacheKey.MENU_USER + resources.getId());
        //    redisUtils.del(CacheKey.ROLE_AUTH + resources.getId());
        //}
        // 如果用户名称修改
        //if(!resources.getUsername().equals(user.getUsername())){
        //    redisUtils.del("user::username:" + user.getUsername());
        //}
        // 如果用户被禁用，则清除用户登录信息
        //if(!resources.getEnabled()){
        //    onlineUserService.kickOutForUsername(resources.getUsername());
        //}
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        //user.setRoles(resources.getRoles());
        //user.setDept(resources.getDept());
        //user.setJobs(resources.getJobs());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        //user.setGender(resources.getGender());
        userRepository.save(user);
        // 清除缓存
        //delCaches(user.getId(), user.getUsername());
    }

    /**
     * @Description: 根据id删除数据
     * @param ids 多条用户id
     */

    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        //for (Long id : ids) {
        //    // 清理缓存
        //    UserDto user = findById(id);
        //    delCaches(user.getId(), user.getUsername());
        //}
        userRepository.deleteAllByIdIn(ids);
    }


}

