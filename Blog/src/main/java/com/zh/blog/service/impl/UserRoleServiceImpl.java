package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.domain.entity.UserRole;
import com.zh.blog.mapper.UserRoleMapper;
import com.zh.blog.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    /**
     * 根据角色id删除用户角色关系
     *
     * @param roleId 角色id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserRoleByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(UserRole::getRoleId, roleId);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据角色id列表批量删除用户角色关系
     *
     * @param roleIdList 角色id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDeleteUserRoleByRoleIdList(List<Long> roleIdList) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.in(Objects.nonNull(roleIdList), UserRole::getRoleId, roleIdList);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据用户id删除用户角色关系
     *
     * @param userId 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserRoleByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(UserRole::getUserId, userId);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据用户id列表批量删除用户角色关系
     *
     * @param userIdList 用户id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDeleteUserRoleByUserIdList(List<Long> userIdList) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.in(UserRole::getUserId, userIdList);
        baseMapper.delete(wrapper);
    }

    /**
     * 根据用户id获取已经分配的角色的id
     *
     * @param userId 用户id
     * @return 已经分配的角色id列表
     */
    @Override
    public List<Long> selectAssignedRoleIdByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        // 优化查询
        wrapper.select(UserRole::getRoleId);
        // 设置条件
        wrapper.eq(UserRole::getUserId, userId);
        // 查询
        List<UserRole> userRoleList = baseMapper.selectList(wrapper);
        return userRoleList.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }
}
