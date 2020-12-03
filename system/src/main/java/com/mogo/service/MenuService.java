package com.mogo.service;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mogo.domain.dto.MenuDto;
import com.mogo.domain.dto.RoleSmallDto;
import com.mogo.domain.entity.Menu;
import com.mogo.domain.mapstruct.MenuMapper;
import com.mogo.domain.qc.MenuQueryCriteria;
import com.mogo.domain.vo.MenuMetaVo;
import com.mogo.domain.vo.MenuVo;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;
import com.mogo.exception.DuplicateKeyException;
import com.mogo.repository.MenuRepository;
import com.mogo.util.QueryHelp;
import com.mogo.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
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
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final RoleService roleService;

    /**
     * 用户角色改变时需清理缓存
     * @param currentUserId /
     * @return /
     */
    public List<MenuDto> findByUser(Long currentUserId) {
        List<RoleSmallDto> roles = roleService.findByUsersId(currentUserId);
        Set<Long> roleIds = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
        roleIds.forEach(e->log.error("角色"+e));
        LinkedHashSet<Menu> menus = menuRepository.findByRoleIdsAndTypeNot(roleIds, 2);
        return menus.stream().map(this:: conv).collect(Collectors.toList());
    }
    private MenuDto conv(Menu menu) {
        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(menu, menuDto);
        return menuDto;
    }

    public List<MenuDto> buildTree(List<MenuDto> menuDtos) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO.getPid() == null) {
                trees.add(menuDTO);
            }
            for (MenuDto it : menuDtos) {
                if (menuDTO.getId().equals(it.getPid())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        if(trees.size() == 0){
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }
    public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO!=null){
                        List<MenuDto> menuDtoList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName())  ? menuDTO.getComponentName() : menuDTO.getTitle());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == null ? "/" + menuDTO.getPath() :menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if(!menuDTO.getIFrame()){
                            if(menuDTO.getPid() == null){
                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                            }else if(!StrUtil.isEmpty(menuDTO.getComponent())){
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getTitle(),menuDTO.getIcon(),!menuDTO.getCache()));
                        if(menuDtoList !=null && menuDtoList.size()!=0){
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if(menuDTO.getPid() == null){
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if(!menuDTO.getIFrame()){
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }
    public List<MenuDto> queryAll(MenuQueryCriteria criteria, Boolean isQuery) throws Exception {
        //Sort sort = new Sort(Sort.Direction.ASC, "menuSort");
        if(isQuery){
            criteria.setPidIsNull(true);
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(criteria);
                if("pidIsNull".equals(field.getName())){
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        List menuList = menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), Sort.by(Sort.Direction.ASC, "menuSort"));
        return menuMapper.toDto(menuList);
    }
    public MenuDto findById(long id) {
        Menu menu = menuRepository.findById(id).orElseGet(Menu::new);
        if (ObjectUtils.isEmpty(menu.getId())) {
            throw new ApiException(ResponseEnum.MENU_NOT_EXISTED);
        }
        return menuMapper.toDto(menu);
    }
    @Transactional(rollbackFor = Exception.class)
    public void create(Menu resources) {
        if(menuRepository.findByTitle(resources.getTitle()) != null){
            throw new ApiException(ResponseEnum.USER_LOGIN_ERROR);
        }
        if(StringUtils.isNotBlank(resources.getComponentName())){
            if(menuRepository.findByComponentName(resources.getComponentName()) != null){
                throw new DuplicateKeyException(Menu.class,"componentName",resources.getComponentName());
            }
        }
        if(resources.getPid().equals(0L)){
            resources.setPid(null);
        }
        if(resources.getIFrame()){
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http)||resources.getPath().toLowerCase().startsWith(https))) {
                throw new ApiException(ResponseEnum.EXTERNAL_LINKS_EXCEPTION);
            }
        }
        menuRepository.save(resources);
        // 计算子节点数目
        resources.setSubCount(0);
        // 更新父节点菜单数目
        updateSubCnt(resources.getPid());
    }
    private void updateSubCnt(Long menuId){
        if(menuId != null){
            int count = menuRepository.countByPid(menuId);
            menuRepository.updateSubCntById(count, menuId);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(Menu resources) {
        if(resources.getId().equals(resources.getPid())) {
            throw new ApiException(ResponseEnum.MENU_PARENT_CANNOT_ITSELF);
        }
        Menu menu = menuRepository.findById(resources.getId()).orElseGet(Menu::new);
        ValidationUtil.isNull(menu,"id",resources.getId());


        if(resources.getIFrame()){
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http)||resources.getPath().toLowerCase().startsWith(https))) {
                throw new ApiException(ResponseEnum.EXTERNAL_LINKS_EXCEPTION);
            }
        }
        Menu menu1 = menuRepository.findByTitle(resources.getTitle());

        if(menu1 != null && !menu1.getId().equals(menu.getId())){
            throw new DuplicateKeyException(Menu.class,"title",resources.getTitle());
        }

        if(resources.getPid().equals(0L)){
            resources.setPid(null);
        }

        // 记录的父节点ID
        Long oldPid = menu.getPid();
        Long newPid = resources.getPid();

        if(StringUtils.isNotBlank(resources.getComponentName())){
            menu1 = menuRepository.findByComponentName(resources.getComponentName());
            if(menu1 != null && !menu1.getId().equals(menu.getId())){
                throw new DuplicateKeyException(Menu.class,"componentName",resources.getComponentName());
            }
        }
        menu.setTitle(resources.getTitle());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setMenuSort(resources.getMenuSort());
        menu.setCache(resources.getCache());
        menu.setHidden(resources.getHidden());
        menu.setComponentName(resources.getComponentName());
        menu.setPermission(resources.getPermission());
        menu.setType(resources.getType());
        menuRepository.save(menu);
        // 计算父级菜单节点数目
        updateSubCnt(oldPid);
        updateSubCnt(newPid);
        // 清理缓存
        //delCaches(resources.getId());
    }
    public Set<Menu> getChildMenus(List<Menu> menuList, Set<Menu> menuSet) {
        for (Menu menu : menuList) {
            menuSet.add(menu);
            List<Menu> menus = menuRepository.findByPid(menu.getId());
            if(menus!=null && menus.size()!=0){
                getChildMenus(menus, menuSet);
            }
        }
        return menuSet;
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Menu> menuSet) {
        for (Menu menu : menuSet) {
            // 清理缓存
            //delCaches(menu.getId());
            roleService.untiedMenu(menu.getId());
            menuRepository.deleteById(menu.getId());
            updateSubCnt(menu.getPid());
        }
    }
    public List<MenuDto> getMenus(Long pid) {
        List<Menu> menus;
        if(pid != null && !pid.equals(0L)){
            menus = menuRepository.findByPid(pid);
        } else {
            menus = menuRepository.findByPidIsNull();
        }
        return menuMapper.toDto(menus);
    }
    public List<MenuDto> getSuperior(MenuDto menuDto, List<Menu> menus) {
        if(menuDto.getPid() == null){
            menus.addAll(menuRepository.findByPidIsNull());
            return menuMapper.toDto(menus);
        }
        menus.addAll(menuRepository.findByPid(menuDto.getPid()));
        return getSuperior(findById(menuDto.getPid()), menus);
    }
    public Menu findOne(Long id) {
        Menu menu = menuRepository.findById(id).orElseGet(Menu::new);
        ValidationUtil.isNull(menu,"id",id);
        return menu;
    }
    public void download(List<MenuDto> menuDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDto menuDTO : menuDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("菜单标题", menuDTO.getTitle());
            map.put("菜单类型", menuDTO.getType() == null ? "目录" : menuDTO.getType() == 1 ? "菜单" : "按钮");
            map.put("权限标识", menuDTO.getPermission());
            map.put("外链菜单", menuDTO.getIFrame() ? "是" : "否");
            map.put("菜单可见", menuDTO.getHidden() ? "否" : "是");
            map.put("是否缓存", menuDTO.getCache() ? "是" : "否");
            map.put("创建日期", menuDTO.getCreateTime());
            list.add(map);
        }
        //FileUtil.downloadExcel(list, response);
    }

}

