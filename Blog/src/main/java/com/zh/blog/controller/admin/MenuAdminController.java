package com.zh.blog.controller.admin;


import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.MenuAddAdminDTO;
import com.zh.blog.domain.dto.admin.MenuUpdateAdminDTO;
import com.zh.blog.domain.entity.Menu;
import com.zh.blog.domain.vo.admin.MenuRoleAdminVO;
import com.zh.blog.domain.vo.admin.MenuTreeAdminVO;
import com.zh.blog.domain.vo.admin.MenuTreeSelectAdminVO;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
@Api(tags = "管理员菜单api中心")
@RestController
@RequestMapping("/admin/menu")
public class MenuAdminController {
    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "获取所有的菜单")
    @GetMapping("getAllMenu")
    public Result getAllMenu() {
        List<MenuTreeAdminVO> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }

    @ApiOperation(value = "获取treeSelect组件所需的菜单")
    @GetMapping("getTreeSelectMenu")
    public Result getTreeSelectMenu() {
        List<MenuTreeSelectAdminVO> treeSelectAdminVOList = menuService.getTreeSelectMenu();
        return Result.success(treeSelectAdminVOList);
    }

    @ApiOperation(value = "根据角色id获取菜单(其拥有的权限将被选中)")
    @GetMapping("getAssignedMenu/{roleId}")
    public Result getAssignedMenu(
            @ApiParam(name = "roleId", value = "角色id", required = true)
            @PathVariable Long roleId) {
        // 查询所有的菜单
        List<MenuTreeSelectAdminVO> menuTree = menuService.getTreeSelectMenu();
        // 查询角色拥有的菜单id
        List<Long> menuIdList = menuService.getMenuIdByRoleId(roleId);
        // 封装为VO并返回
        MenuRoleAdminVO roleMenuVO = new MenuRoleAdminVO(menuIdList, menuTree);
        return Result.success(roleMenuVO);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("addMenu")
    public Result addMenu(
            @ApiParam(name = "addAdminDTO", value = "添加菜单的数据", required = true)
            @Validated @RequestBody MenuAddAdminDTO addAdminDTO) {
        menuService.addMenu(addAdminDTO);
        return Result.success();
    }

    @ApiOperation(value = "更新菜单信息")
    @PutMapping("updateMenu")
    public Result updateMenu(
            @ApiParam(name = "updateAdminDTO", value = "更新菜单的数据", required = true)
            @Validated @RequestBody MenuUpdateAdminDTO updateAdminDTO) {
        menuService.updateMenu(updateAdminDTO);
        return Result.success();
    }

    @ApiOperation(value = "给id为roleId的角色分配菜单")
    @PutMapping("assignRoleMenu/{roleId}")
    public Result assignRoleMenu(
            @ApiParam(name = "roleId", value = "角色id", required = true)
            @PathVariable Long roleId,
            @ApiParam(name = "menuIdList", value = "菜单id列表", required = true)
            @RequestParam("menuIdList") Long[] menuIdList) {
        menuService.assignRoleMenu(roleId, menuIdList);
        return Result.success();
    }

    @ApiOperation(value = "启用或者禁用菜单")
    @PutMapping("enableOrDisableMenu/{menuId}")
    public Result enableOrDisableMenu(
            @ApiParam(name = "menuId", value = "菜单id", required = true)
            @PathVariable Long menuId,
            @ApiParam(name = "status", value = "菜单状态", required = true)
                    Boolean status) {
        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setEnable(status);
        menuService.updateById(menu);
        return Result.success();
    }

    @ApiOperation(value = "根据菜单id删除菜单(存在下级菜单则无法删除)")
    @DeleteMapping("deleteMenu/{menuId}")
    public Result deleteMenu(
            @ApiParam(name = "menuId", value = "菜单id", required = true)
            @PathVariable Long menuId) {
        // 判断是否存在子节点
        if (menuService.existChildMenu(menuId)) {
            throw new BlogException(ResultErrorEnum.MENU_EXITS_CHILD_DELETE_FAILURE);
        }
        // 删除菜单,角色菜单关系
        menuService.deleteMenu(menuId);
        return Result.success();
    }
}

