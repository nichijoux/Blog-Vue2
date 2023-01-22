package com.zh.blog.handler.security;

import com.zh.blog.domain.Result;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseUtil.out(response, Result.failure().message(
                ResultErrorEnum.NO_OPERATOR_PERMISSION.getMessage()
        ));
    }
}
