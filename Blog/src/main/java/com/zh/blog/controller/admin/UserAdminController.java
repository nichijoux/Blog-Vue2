package com.zh.blog.controller.admin;

import com.zh.blog.constants.SystemConstants;
import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.UserAddAdminDTO;
import com.zh.blog.domain.dto.admin.UserAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.UserUpdateAdminDTO;
import com.zh.blog.domain.entity.User;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.service.UserService;
import com.zh.blog.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员用户api中心")
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "分页查询用户信息")
    @PreAuthorize("@PS.hasPermission('permission::user::manage::get')")
    @GetMapping("pageQueryUser/{index}/{limit}")
    public Result pageQueryUser(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "queryConditionDTO", value = "查询条件")
            @Validated UserAdminQueryConditionDTO queryConditionDTO) {
        PageVO pageVO = userService.pageQueryUser(index, limit, queryConditionDTO);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "启用或者禁用用户")
    @PreAuthorize("@PS.hasPermission('permission::user::manage::enableDisable')")
    @PutMapping("enableOrDisableUser/{userId}")
    public Result enableOrDisableUser(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @PathVariable Long userId,
            @ApiParam(name = "status", value = "用户状态", required = true)
            @RequestParam("status") Boolean status) {
        if (SecurityUtils.getUserId().equals(userId)) {
            throw new BlogException(ResultErrorEnum.USER_IS_USING_CHANGE_FAILURE);
        }
        if (userId.equals(SystemConstants.SUPER_ADMIN_ID)) {
            throw new BlogException(ResultErrorEnum.USER_CANT_BE_CHANGE);
        }
        User user = new User();
        user.setId(userId);
        user.setEnable(status);
        userService.updateById(user);
        return Result.success();
    }

    @ApiOperation(value = "添加管理员用户信息")
    @PreAuthorize("@PS.hasPermission('permission::user::manage::add')")
    @PostMapping("addUser")
    public Result addUser(
            @ApiParam(name = "addAdminDTO", value = "添加的管理员用户信息", required = true)
            @Validated @RequestBody UserAddAdminDTO addAdminDTO) {
        userService.addAdminUser(addAdminDTO);
        return Result.success();
    }

    @ApiOperation(value = "更新用户信息")
    @PreAuthorize("@PS.hasPermission('permission::user::manage::update')")
    @PutMapping("updateUser")
    public Result updateUser(
            @ApiParam(name = "updateAdminDTO", value = "更新管理员用户的信息", required = true)
            @Validated @RequestBody UserUpdateAdminDTO updateAdminDTO) {
        userService.updateAdminUser(updateAdminDTO);
        return Result.success();
    }

    @ApiOperation(value = "根据用户id删除用户")
    @PreAuthorize("@PS.hasPermission('permission::user::manage::delete')")
    @DeleteMapping("deleteUser/{userId}")
    public Result deleteUser(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @PathVariable Long userId) {
        // 不能删除当前用户
        if (SecurityUtils.getUserId().equals(userId)) {
            throw new BlogException(ResultErrorEnum.USER_IS_USING_DELETE_FAILURE);
        }
        // 不能删除超级管理员
        if (userId.equals(SystemConstants.SUPER_ADMIN_ID)) {
            throw new BlogException(ResultErrorEnum.USER_CANT_BE_DELETE);
        }
        userService.deleteUser(userId);
        return Result.success();
    }

    @ApiOperation(value = "根据用户id列表批量删除用户")
    @PreAuthorize("@PS.hasPermission('permission::user::manage::batchDelete')")
    @DeleteMapping("batchDeleteUser")
    public Result batchDeleteUser(
            @ApiParam(name = "userIdList", value = "用户id列表", required = true)
            @RequestBody List<Long> userIdList) {
        // 不能删除当前用户
        if (userIdList.contains(SecurityUtils.getUserId())) {
            throw new BlogException(ResultErrorEnum.USER_IS_USING_DELETE_FAILURE);
        }
        // 不能删除超级管理员
        if (userIdList.contains(SystemConstants.SUPER_ADMIN_ID)) {
            throw new BlogException(ResultErrorEnum.USER_CANT_BE_DELETE);
        }
        userService.batchDeleteUser(userIdList);
        return Result.success();
    }
}
