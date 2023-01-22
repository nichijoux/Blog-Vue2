package com.zh.blog.service.impl;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.dto.LoginDTO;
import com.zh.blog.domain.entity.LoginUser;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.service.AdminLoginService;
import com.zh.blog.utils.JwtUtils;
import com.zh.blog.utils.RedisCache;
import com.zh.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private AuthenticationManager authenticationManager;

    private RedisCache redisCache;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 用户登录
     *
     * @param user 登录输入的数据
     */
    @Override
    public String login(LoginDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getAccount(), user.getPassword()
        );
        // 进行鉴权
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new BlogException(ResultErrorEnum.LOGIN_FAILURE);
        }
        // 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtils.createJWT(userId);
        // 将用户信息存入redis
        redisCache.setCacheObject(RedisConstants.BLOG_USER_INFO_KEY + userId, loginUser);
        // 将token封装返回
        return token;
    }

    /**
     * 管理员退出登录
     */
    @Override
    public void logout() {
        // 获取当前登录用户id
        Long userId = SecurityUtils.getUserId();
        // 删除redis中对应的值
        redisCache.deleteObject(RedisConstants.BLOG_USER_INFO_KEY + userId);
    }
}
