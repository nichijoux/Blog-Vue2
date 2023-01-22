package com.zh.blog.controller.user;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.user.RegisterUserDTO;
import com.zh.blog.domain.dto.user.UserUpdateUserDTO;
import com.zh.blog.domain.vo.user.UserInfoVO;
import com.zh.blog.service.MailService;
import com.zh.blog.service.UserService;
import com.zh.blog.utils.RandomUtils;
import com.zh.blog.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api(tags = "普通用户用户信息api中心")
@RestController
@RequestMapping("/user")
public class UserUserController {
    private UserService userService;

    private MailService mailService;

    private RedisCache redisCache;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("getUserInfo")
    public Result getUserInfo() {
        UserInfoVO userInfoVO = userService.getUserInfo();
        return Result.success(userInfoVO);
    }

    @ApiOperation(value = "获取操作的验证码")
    @GetMapping("getValidateCode")
    public Result getValidateCode(
            @ApiParam(name = "email", value = "要发送的验证码", required = true)
            @RequestParam("email") String email,
            @ApiParam(name = "request", value = "http请求", required = true)
                    HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String id = session.getId();
        // 如果redis中存在,则直接返回
        String code = redisCache.getCacheObject(RedisConstants.VALIDATE_CODE + id);
        // redis中不存在则生成验证码
        if (Objects.isNull(code)) {
            code = RandomUtils.getSixBitRandom();
            // 发送邮箱
            mailService.sendValidateCode(email, code);
            // 存入redis,过期时间为5 min
            redisCache.setCacheObject(RedisConstants.VALIDATE_CODE + id, code, 300, TimeUnit.SECONDS);
        }
        return Result.success(code);
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping(value = "updateUserInfo")
    public Result updateUserInfo(
            @ApiParam(name = "userInfoDTO", value = "更新用户信息", required = true)
            @Validated @RequestBody UserUpdateUserDTO userInfoDTO) {
        userService.updateUserInfo(userInfoDTO);
        return Result.success();
    }

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "register")
    public Result register(
            @ApiParam(name = "registerDTO", value = "用户注册输入的数据", required = true)
            @Validated @RequestBody RegisterUserDTO registerDTO) {
        userService.register(registerDTO);
        return Result.success();
    }
}
