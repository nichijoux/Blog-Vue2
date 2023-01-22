package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.domain.entity.RoleMenu;
import com.zh.blog.mapper.RoleMenuMapper;
import com.zh.blog.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    /**
     * 根据菜单id删除角色菜单关系
     *
     * @param menuId 菜单id
     */
    @Override
    public void deleteRoleMenuByMenuId(Long menuId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(RoleMenu::getMenuId, menuId);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据角色id删除角色菜单关系
     *
     * @param roleId 角色id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoleMenuByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(RoleMenu::getRoleId, roleId);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据角色id列表批量删除角色菜单关系
     *
     * @param roleIdList 角色id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDeleteRoleMenu(List<Long> roleIdList) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.in(Objects.nonNull(roleIdList), RoleMenu::getRoleId, roleIdList);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据角色id获取其所拥有的的菜单id
     *
     * @param roleId 角色id
     * @return 已经分配的菜单的id列表
     */
    @Override
    public List<Long> selectMenuIdByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        // 查询优化
        wrapper.select(RoleMenu::getMenuId);
        // 设置查询条件
        wrapper.eq(RoleMenu::getRoleId, roleId);
        // 查询
        List<RoleMenu> roleMenuList = baseMapper.selectList(wrapper);
        return roleMenuList.stream().map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
    }
}
