package com.zh.blog.controller.user;

import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.LoginDTO;
import com.zh.blog.domain.vo.user.BlogUserLoginVO;
import com.zh.blog.service.UserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "普通用户登录api中心")
@RestController
@RequestMapping("/user")
public class UserLoginController {

    private UserLoginService userLoginService;

    @Autowired
    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public Result login(
            @ApiParam(name = "user", value = "要传入的实体", required = true)
            @Validated @RequestBody LoginDTO user) {
        BlogUserLoginVO blogUserLoginVO = userLoginService.login(user);
        return Result.success(blogUserLoginVO);
    }

    @ApiOperation(value = "登出")
    @PostMapping("logout")
    public Result logout() {
        userLoginService.logout();
        return Result.success();
    }
}
