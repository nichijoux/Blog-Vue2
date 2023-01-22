package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zh.blog.constants.SystemConstants;
import com.zh.blog.domain.entity.LoginUser;
import com.zh.blog.domain.entity.User;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.mapper.MenuMapper;
import com.zh.blog.mapper.RoleMapper;
import com.zh.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    private MenuMapper menuMapper;

    private RoleMapper roleMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 设定查询条件
        wrapper.eq(User::getAccount, account);
        wrapper.last("limit 1");
        // 查询一个
        User user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            throw new BlogException(ResultErrorEnum.LOGIN_FAILURE);
        }
        List<String> permissionValueList = null;
        List<String> roleList = null;
        // 如果是管理员用户
        if (user.getType().equals(SystemConstants.ADMIN_USER_TYPE)) {
            // 查询管理员的权限值
            permissionValueList = menuMapper.selectPermissionValueByUserId(user.getId());
            // 查询管理员的角色
            roleList = roleMapper.selectKeyByUserId(user.getId());
        }
        return new LoginUser(user, permissionValueList, roleList);
    }
}
