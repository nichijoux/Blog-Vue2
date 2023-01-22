package com.zh.blog.controller.admin;

import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.LoginDTO;
import com.zh.blog.domain.entity.LoginUser;
import com.zh.blog.domain.vo.admin.AdminUserInfoVO;
import com.zh.blog.domain.vo.admin.MenuTreeAdminVO;
import com.zh.blog.domain.vo.user.UserInfoVO;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.service.AdminLoginService;
import com.zh.blog.service.MenuService;
import com.zh.blog.service.RoleService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员登录api中心")
@RestController
@RequestMapping("/admin")
public class AdminLoginController {

    private AdminLoginService adminLoginService;

    private MenuService menuService;

    private RoleService roleService;

    @Autowired
    public void setAdminLoginService(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "管理员登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(name = "user", value = "用户登录信息", required = true)
            @Validated @RequestBody LoginDTO user) {
        String token = adminLoginService.login(user);
        return Result.success(token);
    }

    @ApiOperation(value = "管理员获取信息")
    @GetMapping("getInfo")
    public Result getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getId();
        // 根据用户id获取权限信息
        List<String> permissionValueList = menuService.getPermissionValueByUserId(userId);
        // 根据用户id获取角色信息
        List<String> roleKeyList = roleService.getRoleKeyByUserId(userId);
        if (roleKeyList.isEmpty()) {
            throw new BlogException(ResultErrorEnum.USER_ROLE_IS_EMPTY);
        }
        // 封装为VO
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        AdminUserInfoVO adminUserInfoVO = new AdminUserInfoVO(roleKeyList, permissionValueList, userInfoVO);
        return Result.success(adminUserInfoVO);
    }

    @ApiOperation(value = "管理员获取router")
    @GetMapping("getRouters")
    public Result getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询menu,结果为树型结构
        List<MenuTreeAdminVO> menuList = menuService.getRoutersTreeByUserId(userId);
        // 返回menuList
        return Result.success(menuList);
    }

    @ApiOperation(value = "管理员退出登录")
    @PostMapping("logout")
    public Result logout() {
        adminLoginService.logout();
        return Result.success();
    }
}
