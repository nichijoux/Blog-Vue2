package com.zh.blog.utils;

import com.zh.blog.constants.SystemConstants;
import com.zh.blog.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 是否为究极管理员
     *
     * @return 是否为究极管理员
     */
    public static Boolean isSuperAdmin() {
        Long userId = getLoginUser().getUser().getId();
        return userId != null && userId.equals(SystemConstants.SUPER_ADMIN_ID);
    }

    /**
     * 获取用户id
     *
     * @return 获取user表所对应的主键
     */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
