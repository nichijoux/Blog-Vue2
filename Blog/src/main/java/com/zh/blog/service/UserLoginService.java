package com.zh.blog.service;

import com.zh.blog.domain.dto.LoginDTO;
import com.zh.blog.domain.vo.user.BlogUserLoginVO;

public interface UserLoginService {
    BlogUserLoginVO login(LoginDTO user);

    void logout();
}
