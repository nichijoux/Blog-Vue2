package com.zh.blog.service.impl;

import com.zh.blog.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PS")
public class PermissionService {
    /**
     * 判断当前用户是否具有permission
     *
     * @param permission 要判断的权限
     * @return 是否具有权限
     */
    public boolean hasPermission(String permission) {
        //如果是超级管理员,直接返回true
        if (SecurityUtils.isSuperAdmin()) {
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissionValueList = SecurityUtils.getLoginUser().getPermissionValueList();
        return permissionValueList.contains(permission);
    }

    /**
     * 判断当前用户是否拥有某个角色
     *
     * @param role 当前角色
     * @return 是否拥有角色
     */
    public boolean hasRole(String role) {
        //如果是超级管理员,直接返回true
        if (SecurityUtils.isSuperAdmin()) {
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> roleList = SecurityUtils.getLoginUser().getRoleList();
        return roleList.contains(role);
    }
}
