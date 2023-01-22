package com.zh.blog.filter;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.entity.LoginUser;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.utils.JwtUtils;
import com.zh.blog.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登录,直接放心
            filterChain.doFilter(request, response);
            return;
        }
        // 解析获取userId
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token超时,token非法
            throw new BlogException(ResultErrorEnum.TOKEN_ERROR_LOGIN_AGAIN);
        }
        String userId = claims.getSubject();
        // 获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(RedisConstants.BLOG_USER_INFO_KEY + userId);
        if (Objects.isNull(loginUser)) {
            // 如果获取不到则说明登录过期
            throw new BlogException(ResultErrorEnum.TOKEN_ERROR_LOGIN_AGAIN);
        }
        // 否则更新过期时间
        redisCache.expire(RedisConstants.BLOG_USER_INFO_KEY + userId, 30, TimeUnit.MINUTES);
        // 设置authentication,参数为主体、登录凭证、权限列表
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, loginUser.getUser().getPassword(), loginUser.getAuthorities()
        );
        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
