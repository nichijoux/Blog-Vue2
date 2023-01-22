package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.domain.dto.admin.RoleAdminQueryConditionDTO;
import com.zh.blog.domain.entity.Role;
import com.zh.blog.domain.entity.UserRole;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.RoleEnableAdminVO;
import com.zh.blog.domain.vo.admin.RoleListAdminVO;
import com.zh.blog.domain.vo.admin.RoleTransferAdminVO;
import com.zh.blog.mapper.RoleMapper;
import com.zh.blog.service.RoleMenuService;
import com.zh.blog.service.RoleService;
import com.zh.blog.service.UserRoleService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private UserRoleService userRoleService;

    private RoleMenuService roleMenuService;

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setRoleMenuService(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * 获取所有已经启用的角色
     *
     * @return 所有已经启用的角色
     */
    @Override
    public List<RoleEnableAdminVO> getAllEnableRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Role::getId, Role::getName);
        // 设置查询条件
        wrapper.eq(Role::getEnable, true);
        // 查询
        List<Role> roleList = baseMapper.selectList(wrapper);
        // 封装后返回
        return BeanCopyUtils.copyBeanList(roleList, RoleEnableAdminVO.class);
    }

    /**
     * 根据用户id获取角色Key值(用于Spring Security)
     *
     * @param userId 用户id
     * @return 角色key值列表
     */
    @Override
    public List<String> getRoleKeyByUserId(Long userId) {
        // 判断用户是否为super_admin用户,是则返回的集合只需要有super_admin
        if (SecurityUtils.isSuperAdmin()) {
            List<String> roleKeyList = new ArrayList<>();
            roleKeyList.add("super_admin");
            return roleKeyList;
        }
        // 否则则进行查询
        return baseMapper.selectKeyByUserId(userId);
    }

    /**
     * 管理员分页查询角色信息
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO pageQueryRole(Long index, Long limit, RoleAdminQueryConditionDTO queryConditionDTO) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Role::getId, Role::getName, Role::getKey,
                Role::getRemark, Role::getSort, Role::getEnable);
        // 设置查询条件
        if (Objects.nonNull(queryConditionDTO)) {
            // 角色名
            String name = queryConditionDTO.getName();
            wrapper.like(Objects.nonNull(name), Role::getName, name);
            // 角色权限字符串
            String roleKey = queryConditionDTO.getKey();
            wrapper.like(Objects.nonNull(roleKey), Role::getKey, roleKey);
            // 用户是否启用
            Boolean enable = queryConditionDTO.getEnable();
            wrapper.eq(Objects.nonNull(enable), Role::getEnable, enable);
        }
        // 设置排序
        wrapper.orderByAsc(Role::getSort);
        wrapper.orderByDesc(Role::getCreateTime);
        // 分页查询
        Page<Role> rolePage = new Page<>(index, limit);
        baseMapper.selectPage(rolePage, wrapper);
        List<RoleListAdminVO> roleListAdminVOList = BeanCopyUtils.copyBeanList(rolePage.getRecords(), RoleListAdminVO.class);
        // 封装为PageVO
        return BeanCopyUtils.copyPageVO(rolePage, roleListAdminVOList);
    }

    /**
     * 根据角色id删除角色(包括角色-权限关系和用户-角色关系)
     *
     * @param roleId 角色id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoleByRoleId(Long roleId) {
        // 删除用户角色关系
        userRoleService.deleteUserRoleByRoleId(roleId);
        // 删除角色菜单关系
        roleMenuService.deleteRoleMenuByRoleId(roleId);
        // 删除角色
        baseMapper.deleteById(roleId);
    }

    /**
     * 根据角色id列表批量删除角色(包括角色-权限关系和用户-角色关系)
     *
     * @param roleIdList 角色id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDeleteRole(List<Long> roleIdList) {
        // 删除用户角色关系
        userRoleService.batchDeleteUserRoleByRoleIdList(roleIdList);
        // 删除角色菜单关系
        roleMenuService.batchDeleteRoleMenu(roleIdList);
        // 删除角色
        baseMapper.deleteBatchIds(roleIdList);
    }

    /**
     * 获取所有角色(仅包含id和name列)
     *
     * @return RoleTransferAdminVO列表
     */
    @Override
    public List<RoleTransferAdminVO> getAllTransferRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Role::getId, Role::getName);
        // 查询
        List<Role> roleList = baseMapper.selectList(wrapper);
        // 封装后返回
        return BeanCopyUtils.copyBeanList(roleList, RoleTransferAdminVO.class);
    }

    /**
     * 根据用户id获取已经分配的角色的id
     *
     * @param userId 用户id
     * @return 已经分配的角色id列表
     */
    @Override
    public List<Long> getAssignedRoleIdByUserId(Long userId) {
        return userRoleService.selectAssignedRoleIdByUserId(userId);
    }

    /**
     * 为用户分配角色
     *
     * @param userId     要分配的用户id
     * @param roleIdList 角色id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRole(Long userId, Long[] roleIdList) {
        // 删除用户角色关系
        userRoleService.deleteUserRoleByUserId(userId);
        // 插入用户角色关系
        List<UserRole> userRoleList = Arrays.stream(roleIdList).map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            return userRole;
        }).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }
}
