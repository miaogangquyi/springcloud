package com.mogo.service;


import com.mogo.domain.Role;
import com.mogo.domain.User;
import com.mogo.domain.dto.RoleDto;
import com.mogo.domain.mapstruct.RoleMapper;
import com.mogo.domain.qc.RoleQueryCriteria;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import com.mogo.repository.RoleRepository;
import com.mogo.repository.UserRepository;
import com.mogo.util.QueryHelp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: miaogang
 * @Date: 2020年11月19日
 * @Description: 用户服务类
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;


    public List<RoleDto> queryAll() {
        return roleMapper.toDto(roleRepository.findAll(Sort.by(Sort.Direction.ASC,"level")));
    }

    public List<RoleDto> queryAll(RoleQueryCriteria criteria) {
        return roleMapper.toDto(roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    public Page<Role> queryAll(RoleQueryCriteria criteria, Pageable pageable) {
        return  roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public RoleDto findById(long id) {
        Role role = roleRepository.findById(id).orElseGet(Role::new);
        if (ObjectUtils.isEmpty(role.getId())) {
            throw new ApiException(ResponseEnum.ROLE_NOT_EXISTED);
        }
        return roleMapper.toDto(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(Role role) {
        Role roleInfo = roleRepository.findByName(role.getName());
        if (ObjectUtils.isEmpty(roleInfo)) {
            throw new ApiException(ResponseEnum.ROLE_NAME_EXISTED);
        }
        roleRepository.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {
        Role role = roleRepository.findById(resources.getId()).orElseGet(Role::new);
        if (ObjectUtils.isEmpty(role.getId())) {
            throw new ApiException(ResponseEnum.ROLE_NOT_EXISTED);
        }
        Role role1 = roleRepository.findByName(resources.getName());

        if (role1 != null && !role1.getId().equals(role.getId())) {
            throw new ApiException(ResponseEnum.ROLE_NAME_EXISTED);
        }
        role.setName(resources.getName());
        role.setDescription(resources.getDescription());
        //role.setDataScope(resources.getDataScope());
        //role.setDepts(resources.getDepts());
        role.setLevel(resources.getLevel());
        roleRepository.save(role);
        // 更新相关缓存
        //delCaches(role.getId(), null);
    }

    public void updateMenu(Role resources, RoleDto roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        List<User> users = userRepository.findByRoleId(role.getId());
        // 更新菜单
        role.setMenus(resources.getMenus());
        //delCaches(resources.getId(), users);
        roleRepository.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void untiedMenu(Long menuId) {
        // 更新菜单
        roleRepository.untiedMenu(menuId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        //for (Long id : ids) {
        //    // 更新相关缓存
        //    delCaches(id, null);
        //}
        roleRepository.deleteAllByIdIn(ids);
    }

    public List<RoleDto> findByUsersId(Long id) {
        return roleMapper.toDto(new ArrayList<>(roleRepository.findByUserId(id)));
    }

    public Integer findByRoles(Set<Role> roles) {
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Role role : roles) {
            roleDtos.add(findById(role.getId()));
        }
        return Collections.min(roleDtos.stream().map(RoleDto::getLevel).collect(Collectors.toList()));
    }

    //public void download(List<RoleDto> roles, HttpServletResponse response) throws IOException {
    //    List<Map<String, Object>> list = new ArrayList<>();
    //    for (RoleDto role : roles) {
    //        Map<String, Object> map = new LinkedHashMap<>();
    //        map.put("角色名称", role.getName());
    //        map.put("角色级别", role.getLevel());
    //        map.put("描述", role.getDescription());
    //        map.put("创建日期", role.getCreateTime());
    //        list.add(map);
    //    }
    //    FileUtil.downloadExcel(list, response);
    //}

    public void verification(Set<Long> ids) {
        if (userRepository.countByRoles(ids) > 0) {
            throw new ApiException(ResponseEnum.ROLE_RELY_ON_USER);
        }
    }

    public List<Role> findInMenuId(List<Long> menuIds) {
        return roleRepository.findInMenuId(menuIds);
    }





}

