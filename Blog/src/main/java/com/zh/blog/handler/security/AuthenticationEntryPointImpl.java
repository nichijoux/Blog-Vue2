package com.zh.blog.handler.security;

import com.zh.blog.domain.Result;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.utils.ResponseUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        // InsufficientAuthenticationException
        // BadCredentialsException
        Result result;
        if (authException instanceof BadCredentialsException) {
            result = Result.failure().message(authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = Result.failure(ResultErrorEnum.OPERATION_NEED_LOGIN);
        } else {
            result = Result.failure(ResultErrorEnum.AUTHENTICATION_OR_AUTHORIZATION_ERROR);
        }
        //响应给前端
        ResponseUtil.out(response, result);
    }
}
