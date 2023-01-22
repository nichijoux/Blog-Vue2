package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.constants.SystemConstants;
import com.zh.blog.domain.dto.admin.MenuAddAdminDTO;
import com.zh.blog.domain.dto.admin.MenuUpdateAdminDTO;
import com.zh.blog.domain.entity.*;
import com.zh.blog.domain.vo.admin.MenuTreeAdminVO;
import com.zh.blog.domain.vo.admin.MenuTreeSelectAdminVO;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.mapper.MenuMapper;
import com.zh.blog.service.MenuService;
import com.zh.blog.service.RoleMenuService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private RoleMenuService roleMenuService;

    @Autowired
    public void setRoleMenuService(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * 根据用户id获取权限值列表
     *
     * @param userId 用户id
     * @return 权限值列表
     */
    @Override
    public List<String> getPermissionValueByUserId(Long userId) {
        if (SecurityUtils.isSuperAdmin()) {
            // 如果当前用户是admin,则返回所有权限
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            // 优化查询
            wrapper.select(Menu::getPermissionValue);
            // 获取菜单和按钮的权限值,正常情况下目录的权限值应该为空
            wrapper.in(Menu::getType, SystemConstants.MENU_TYPE_MENU, SystemConstants.MENU_TYPE_BUTTON);
            // 应该获取启用的菜单
            wrapper.eq(Menu::getEnable, true);
            // 查询menu
            List<Menu> menuList = baseMapper.selectList(wrapper);
            return menuList.stream().map(Menu::getPermissionValue).collect(Collectors.toList());
        }
        return baseMapper.selectPermissionValueByUserId(userId);
    }

    /**
     * 根据用户id获取路由菜单
     *
     * @param userId 用户id
     * @return 路由菜单
     */
    @Override
    public List<MenuTreeAdminVO> getRoutersTreeByUserId(Long userId) {
        List<Menu> menuList;
        if (SecurityUtils.isSuperAdmin()) {
            // 管理员拥有所有菜单
            menuList = baseMapper.selectAllRouterMenu();
        } else {
            // 其他用户需要根据用户id获取菜单
            menuList = baseMapper.selectRouterMenuByUserId(userId);
        }
        // 构建用户菜单
        List<MenuTreeAdminVO> menuTreeAdminVOList = BeanCopyUtils.copyBeanList(menuList, MenuTreeAdminVO.class);
        return buildMenuTree(menuTreeAdminVOList, SystemConstants.MENU_TREE_ROOT);
    }

    /**
     * 根据menuId判断是否存在其子菜单
     *
     * @param menuId 要判断的菜单id
     * @return 是否存在子菜单
     */
    @Override
    public boolean existChildMenu(Long menuId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        wrapper.eq(Menu::getPid, menuId);
        // 只要存在1条则返回
        wrapper.last("limit 1");
        return baseMapper.selectCount(wrapper) != 0;
    }

    /**
     * 根据menuId删除菜单以及角色-菜单关系
     *
     * @param menuId 菜单id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenu(Long menuId) {
        // 删除角色-菜单关系
        roleMenuService.deleteRoleMenuByMenuId(menuId);
        // 删除菜单
        baseMapper.deleteById(menuId);
    }

    /**
     * 获取所有的菜单(包括按钮)
     *
     * @return 组装后的菜单列表(包括父子关系)
     */
    @Override
    public List<MenuTreeAdminVO> getAllMenu() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Menu::getId, Menu::getPid, Menu::getName,
                Menu::getPermissionValue, Menu::getType, Menu::getPath,
                Menu::getComponent, Menu::getRedirect, Menu::getIcon, Menu::getHidden,
                Menu::getEnable, Menu::getSort);
        // 排序
        wrapper.orderByAsc(Menu::getSort);
        // 查询menu
        List<Menu> menuList = baseMapper.selectList(wrapper);
        List<MenuTreeAdminVO> menuTreeAdminVOList = BeanCopyUtils.copyBeanList(menuList, MenuTreeAdminVO.class);
        return buildMenuTree(menuTreeAdminVOList, SystemConstants.MENU_TREE_ROOT);
    }

    /**
     * 添加菜单信息
     *
     * @param addAdminDTO 添加菜单的DTO
     */
    @Override
    public void addMenu(MenuAddAdminDTO addAdminDTO) {
        if (isMenuTypeSuitable(addAdminDTO.getPid(), SystemConstants.MENU_TYPE_BUTTON)) {
            throw new BlogException(ResultErrorEnum.BUTTON_MENU_CANT_BE_PARENT);
        }
        // 根据菜单类型过滤属性
        Menu menu;
        switch (addAdminDTO.getType()) {
            case 0:
                DirectoryMenu directoryMenu = BeanCopyUtils.copyBean(addAdminDTO, DirectoryMenu.class);
                menu = BeanCopyUtils.copyBean(directoryMenu, Menu.class);
                break;
            case 1:
                MenuMenu menuMenu = BeanCopyUtils.copyBean(addAdminDTO, MenuMenu.class);
                menu = BeanCopyUtils.copyBean(menuMenu, Menu.class);
                break;
            case 2:
                ButtonMenu buttonMenu = BeanCopyUtils.copyBean(addAdminDTO, ButtonMenu.class);
                menu = BeanCopyUtils.copyBean(buttonMenu, Menu.class);
                break;
            default:
                throw new BlogException(ResultErrorEnum.MENU_TYPE_ERROR);
        }
        // 添加menu
        baseMapper.insert(menu);
    }

    /**
     * 更新菜单信息
     *
     * @param updateAdminDTO 更新菜单的DTO
     */
    @Override
    public void updateMenu(MenuUpdateAdminDTO updateAdminDTO) {
        if (isMenuTypeSuitable(updateAdminDTO.getPid(), SystemConstants.MENU_TYPE_BUTTON)) {
            throw new BlogException(ResultErrorEnum.BUTTON_MENU_CANT_BE_PARENT);
        }
        if (updateAdminDTO.getId().equals(updateAdminDTO.getPid())) {
            throw new BlogException(ResultErrorEnum.MENU_ID_PID_ERROR);
        }
        // 将MenuUpdateAdminDTO转换为对应的MenuType类型
        Menu menu;
        switch (updateAdminDTO.getType()) {
            case 0:
                DirectoryMenu directoryMenu = BeanCopyUtils.copyBean(updateAdminDTO, DirectoryMenu.class);
                menu = BeanCopyUtils.copyBean(directoryMenu, Menu.class);
                break;
            case 1:
                MenuMenu menuMenu = BeanCopyUtils.copyBean(updateAdminDTO, MenuMenu.class);
                menu = BeanCopyUtils.copyBean(menuMenu, Menu.class);
                break;
            case 2:
                ButtonMenu buttonMenu = BeanCopyUtils.copyBean(updateAdminDTO, ButtonMenu.class);
                menu = BeanCopyUtils.copyBean(buttonMenu, Menu.class);
                break;
            default:
                throw new BlogException(ResultErrorEnum.MENU_TYPE_ERROR);
        }
        // 更新menu
        menu.setId(updateAdminDTO.getId());
        baseMapper.updateById(menu);
    }

    /**
     * 获取treeSelect组件所需数据
     *
     * @return treeSelect组件所需树形数据
     */
    @Override
    public List<MenuTreeSelectAdminVO> getTreeSelectMenu() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        // 优化查询
        wrapper.select(Menu::getId, Menu::getPid, Menu::getName);
        // 数据库查询
        List<Menu> menuList = baseMapper.selectList(wrapper);
        // 转换为所需VO对象
        List<MenuTreeSelectAdminVO> treeList = BeanCopyUtils.copyBeanList(menuList, MenuTreeSelectAdminVO.class);
        // 封装成树形结构
        return treeList.stream()
                .filter(menu -> menu.getPid().equals(SystemConstants.MENU_TREE_ROOT))
                .peek(menu -> menu.setChildren(getMenuChildren(menu, treeList)))
                .collect(Collectors.toList());
    }

    /**
     * 根据角色id获取其所拥有的的菜单id
     *
     * @param roleId 角色id
     * @return 角色拥有的菜单id列表
     */
    @Override
    public List<Long> getMenuIdByRoleId(Long roleId) {
        return roleMenuService.selectMenuIdByRoleId(roleId);
    }

    /**
     * 给角色分配菜单权限
     *
     * @param roleId     角色id
     * @param menuIdList 菜单id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoleMenu(Long roleId, Long[] menuIdList) {
        // 先删除roleMenu
        roleMenuService.deleteRoleMenuByRoleId(roleId);
        // 插入roleId-menuId
        List<RoleMenu> roleMenuList = Arrays.stream(menuIdList).map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());
        // 批量插入
        roleMenuService.saveBatch(roleMenuList);
    }

    /**
     * 构建树型菜单
     *
     * @param menuList 所有的菜单列表
     * @param parentId 根节点的id
     * @return 构建完成的树型菜单
     */
    private List<MenuTreeAdminVO> buildMenuTree(List<MenuTreeAdminVO> menuList, Long parentId) {
        return menuList.stream()
                .filter(menu -> menu.getPid().equals(parentId))
                .peek(menu -> menu.setChildren(getMenuChildren(menu, menuList)))
                .collect(Collectors.toList());
    }

    /**
     * 获取父节点(父菜单)的子节点(子菜单)
     *
     * @param parent   父节点
     * @param menuList 所有的menu菜单
     * @return parent的子节点列表
     */
    private List<MenuTreeAdminVO> getMenuChildren(MenuTreeAdminVO parent, List<MenuTreeAdminVO> menuList) {
        return menuList.stream()
                .filter(menu -> menu.getPid().equals(parent.getId()))
                .peek(menu -> menu.setChildren(getMenuChildren(menu, menuList)))
                .collect(Collectors.toList());
    }

    private List<MenuTreeSelectAdminVO> getMenuChildren(MenuTreeSelectAdminVO parent, List<MenuTreeSelectAdminVO> menuList) {
        return menuList.stream()
                .filter(menu -> menu.getPid().equals(parent.getId()))
                .peek(menu -> menu.setChildren(getMenuChildren(menu, menuList)))
                .collect(Collectors.toList());
    }

    /**
     * 判断菜单主键为id的时候,该菜单类型是否为menuType类型
     *
     * @param id       菜单id
     * @param menuType 菜单类型
     * @return 是否为按钮
     */
    private boolean isMenuTypeSuitable(Long id, Integer menuType) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        wrapper.eq(Menu::getId, id);
        wrapper.eq(Menu::getType, menuType);
        wrapper.last("limit 1");
        return baseMapper.selectCount(wrapper) != 0;
    }
}
