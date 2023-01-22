package com.zh.blog.service;

import com.zh.blog.domain.dto.LoginDTO;

public interface AdminLoginService {

    // 用户登录
    String login(LoginDTO user);

    // 注销用户
    void logout();
}
