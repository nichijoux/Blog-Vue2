package com.zh.blog.service.impl;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.dto.LoginDTO;
import com.zh.blog.domain.entity.LoginUser;
import com.zh.blog.domain.vo.user.BlogUserLoginVO;
import com.zh.blog.domain.vo.user.UserInfoVO;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.service.UserLoginService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.JwtUtils;
import com.zh.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserLoginServiceImpl implements UserLoginService {

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
     * @param user 用户信息(包括用户名密码)
     * @return 用户信息实体
     */
    @Override
    public BlogUserLoginVO login(LoginDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getAccount(), user.getPassword());
        // 获取认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new BlogException(ResultErrorEnum.LOGIN_FAILURE);
        }
        // 获取登录实体
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwtToken = JwtUtils.createJWT(userId);
        // 存入redisCache
        redisCache.setCacheObject(RedisConstants.BLOG_USER_INFO_KEY + userId, loginUser);
        // 将token和userInfo封装返回
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        return new BlogUserLoginVO(jwtToken, userInfoVO);
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取用户id
        String userId = loginUser.getUser().getId().toString();
        // 删除对象实体
        redisCache.deleteObject(RedisConstants.BLOG_USER_INFO_KEY + userId);
    }
}
