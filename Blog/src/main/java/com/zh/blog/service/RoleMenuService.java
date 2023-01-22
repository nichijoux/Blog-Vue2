package com.zh.blog.service;

import com.zh.blog.domain.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 根据菜单id删除角色菜单关系
     *
     * @param menuId 菜单id
     */
    void deleteRoleMenuByMenuId(Long menuId);

    /**
     * 根据角色id删除角色菜单关系
     *
     * @param roleId 角色id
     */
    void deleteRoleMenuByRoleId(Long roleId);

    /**
     * 根据角色id列表批量删除角色菜单关系
     *
     * @param roleIdList 角色id列表
     */
    void batchDeleteRoleMenu(List<Long> roleIdList);

    /**
     * 根据角色id获取其所拥有的的菜单id
     *
     * @param roleId 角色id
     * @return 已经分配的菜单的id列表
     */
    List<Long> selectMenuIdByRoleId(Long roleId);
}
