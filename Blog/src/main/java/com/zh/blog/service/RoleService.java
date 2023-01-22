package com.zh.blog.service;

import com.zh.blog.domain.dto.admin.RoleAdminQueryConditionDTO;
import com.zh.blog.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.RoleEnableAdminVO;
import com.zh.blog.domain.vo.admin.RoleTransferAdminVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
public interface RoleService extends IService<Role> {
    /**
     * 获取所有已经启用的角色
     *
     * @return 所有已经启用的角色
     */
    List<RoleEnableAdminVO> getAllEnableRole();

    /**
     * 根据用户id获取角色Key值(用于Spring Security)
     *
     * @param userId 用户id
     * @return 角色key值列表
     */
    List<String> getRoleKeyByUserId(Long userId);

    /**
     * 管理员分页查询角色信息
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 分页结果
     */
    PageVO pageQueryRole(Long index, Long limit, RoleAdminQueryConditionDTO queryConditionDTO);

    /**
     * 根据角色id删除角色(包括角色-权限关系和用户-角色关系)
     *
     * @param roleId 角色id
     */
    void deleteRoleByRoleId(Long roleId);

    /**
     * 根据角色id列表批量删除角色(包括角色-权限关系和用户-角色关系)
     *
     * @param roleIdList 角色id列表
     */
    void batchDeleteRole(List<Long> roleIdList);

    /**
     * 获取所有角色(仅包含id和name列)
     *
     * @return RoleTransferAdminVO列表
     */
    List<RoleTransferAdminVO> getAllTransferRole();

    /**
     * 根据用户id获取已经分配的角色的id
     *
     * @param userId 用户id
     * @return 已经分配的角色id列表
     */
    List<Long> getAssignedRoleIdByUserId(Long userId);

    /**
     * 为用户分配角色
     *
     * @param userId     要分配的用户id
     * @param roleIdList 角色id列表
     */
    void assignRole(Long userId, Long[] roleIdList);
}
