package com.zh.blog.service;

import com.zh.blog.domain.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据角色id删除用户角色关系
     *
     * @param roleId 角色id
     */
    void deleteUserRoleByRoleId(Long roleId);

    /**
     * 根据角色id列表批量删除用户角色关系
     *
     * @param roleIdList 角色id列表
     */
    void batchDeleteUserRoleByRoleIdList(List<Long> roleIdList);

    /**
     * 根据用户id删除用户角色关系
     *
     * @param userId 用户id
     */
    void deleteUserRoleByUserId(Long userId);

    /**
     * 根据用户id列表批量删除用户角色关系
     *
     * @param userIdList 用户id列表
     */
    void batchDeleteUserRoleByUserIdList(List<Long> userIdList);

    /**
     * 根据用户id获取已经分配的角色的id
     *
     * @param userId 用户id
     * @return 已经分配的角色id列表
     */
    List<Long> selectAssignedRoleIdByUserId(Long userId);
}
