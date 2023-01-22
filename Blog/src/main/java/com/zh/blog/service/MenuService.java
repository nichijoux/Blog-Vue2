package com.zh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.dto.admin.MenuAddAdminDTO;
import com.zh.blog.domain.dto.admin.MenuUpdateAdminDTO;
import com.zh.blog.domain.entity.Menu;
import com.zh.blog.domain.vo.admin.MenuTreeAdminVO;
import com.zh.blog.domain.vo.admin.MenuTreeSelectAdminVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据用户id获取权限值列表
     *
     * @param userId 用户id
     * @return 权限值列表
     */
    List<String> getPermissionValueByUserId(Long userId);

    /**
     * 根据用户id获取路由菜单
     *
     * @param userId 用户id
     * @return 路由菜单
     */
    List<MenuTreeAdminVO> getRoutersTreeByUserId(Long userId);

    /**
     * 根据menuId判断是否存在其子菜单
     *
     * @param menuId 要判断的菜单id
     * @return 是否存在子菜单
     */
    boolean existChildMenu(Long menuId);

    /**
     * 根据menuId删除菜单以及角色-菜单关系
     *
     * @param menuId 菜单id
     */
    void deleteMenu(Long menuId);

    /**
     * 获取所有的菜单(包括按钮)
     *
     * @return 组装后的菜单列表(包括父子关系)
     */
    List<MenuTreeAdminVO> getAllMenu();

    /**
     * 添加菜单信息
     *
     * @param addAdminDTO 添加菜单的DTO
     */
    void addMenu(MenuAddAdminDTO addAdminDTO);

    /**
     * 更新菜单信息
     *
     * @param updateAdminDTO 更新菜单的DTO
     */
    void updateMenu(MenuUpdateAdminDTO updateAdminDTO);

    /**
     * 获取treeSelect组件所需数据
     *
     * @return treeSelect组件所需树形数据
     */
    List<MenuTreeSelectAdminVO> getTreeSelectMenu();

    /**
     * 根据角色id获取其所拥有的的菜单id
     *
     * @param roleId 角色id
     * @return 角色拥有的菜单id列表
     */
    List<Long> getMenuIdByRoleId(Long roleId);

    /**
     * 给角色分配菜单权限
     *
     * @param roleId     角色id
     * @param menuIdList 菜单id列表
     */
    void assignRoleMenu(Long roleId, Long[] menuIdList);
}
