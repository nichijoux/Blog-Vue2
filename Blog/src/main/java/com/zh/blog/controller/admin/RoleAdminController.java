package com.zh.blog.controller.admin;


import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.RoleAddAdminDTO;
import com.zh.blog.domain.dto.admin.RoleAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.RoleUpdateAdminDTO;
import com.zh.blog.domain.entity.Role;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.RoleEnableAdminVO;
import com.zh.blog.domain.vo.admin.RoleTransferAdminVO;
import com.zh.blog.domain.vo.admin.UserRoleAdminVO;
import com.zh.blog.service.RoleService;
import com.zh.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
@Api(tags = "管理员角色api中心")
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取所有启用的角色,仅包含id和name")
    @GetMapping("getAllEnableRole")
    public Result getAllEnableRole() {
        List<RoleEnableAdminVO> roleList = roleService.getAllEnableRole();
        return Result.success(roleList);
    }

    @ApiOperation(value = "分页查询角色信息")
    @GetMapping("pageQueryRole/{index}/{limit}")
    public Result pageQueryRole(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "queryConditionDTO", value = "查询条件")
            @Validated RoleAdminQueryConditionDTO queryConditionDTO) {
        PageVO pageVO = roleService.pageQueryRole(index, limit, queryConditionDTO);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "获取用户的角色")
    @GetMapping("getUserRole/{userId}")
    public Result getUserRole(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @PathVariable Long userId) {
        // 获取所有的角色
        List<RoleTransferAdminVO> roleList = roleService.getAllTransferRole();
        // 获取用户的角色
        List<Long> roleIdList = roleService.getAssignedRoleIdByUserId(userId);
        // 封装为VO
        UserRoleAdminVO userRoleAdminVO = new UserRoleAdminVO(roleIdList, roleList);
        return Result.success(userRoleAdminVO);
    }

    @ApiOperation(value = "添加角色信息")
    @PostMapping("addRole")
    public Result addRole(
            @ApiParam(name = "addAdminDTO", value = "添加角色的DTO", required = true)
            @Validated @RequestBody RoleAddAdminDTO addAdminDTO) {
        Role role = BeanCopyUtils.copyBean(addAdminDTO, Role.class);
        roleService.save(role);
        return Result.success();
    }

    @ApiOperation(value = "更新角色信息")
    @PutMapping("updateRole")
    public Result updateRole(
            @ApiParam(name = "updateAdminDTO", value = "更新角色的DTO", required = true)
            @Validated @RequestBody RoleUpdateAdminDTO updateAdminDTO) {
        Role role = BeanCopyUtils.copyBean(updateAdminDTO, Role.class);
        roleService.updateById(role);
        return Result.success();
    }

    @ApiOperation(value = "为用户分配角色")
    @PutMapping("assignUserRole/{userId}")
    public Result assignUserRole(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @PathVariable Long userId,
            @ApiParam(name = "roleIdList", value = "要分配的角色id列表", required = true)
            @RequestParam("roleIdList") Long[] roleIdList) {
        roleService.assignRole(userId, roleIdList);
        return Result.success();
    }

    @ApiOperation(value = "启用或禁用角色")
    @PutMapping("enableOrDisableRole/{roleId}")
    public Result enableOrDisableRole(
            @ApiParam(name = "roleId", value = "角色id", required = true)
            @PathVariable Long roleId,
            @ApiParam(name = "status", value = "角色状态", required = true)
            @RequestParam("status") Boolean status) {
        Role role = new Role();
        role.setId(roleId);
        role.setEnable(status);
        roleService.updateById(role);
        return Result.success();
    }

    @ApiOperation(value = "根据角色id删除角色")
    @DeleteMapping("deleteRole/{roleId}")
    public Result deleteRole(
            @ApiParam(name = "roleId", value = "角色id", required = true)
            @PathVariable Long roleId) {
        roleService.deleteRoleByRoleId(roleId);
        return Result.success();
    }

    @ApiOperation(value = "根据角色id批量删除角色")
    @DeleteMapping("batchDeleteRole")
    public Result batchDeleteRole(
            @ApiParam(name = "roleIdList", value = "角色id列表", required = true)
            @NotEmpty(message = "批量删除的角色id不能为空")
            @RequestBody List<Long> roleIdList) {
        roleService.batchDeleteRole(roleIdList);
        return Result.success();
    }
}