package com.zh.blog.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "用户注册输入的DTO", description = "用户注册输入的DTO")
public class RegisterUserDTO {
    @ApiModelProperty("用户账号,用户账号应该唯一")
    @NotBlank(message = "用户账号不能为空")
    private String account;

    @ApiModelProperty("密码,应该为MD5加密后的密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty("用户注册的邮箱")
    @NotNull
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("邮箱验证码")
    @NotNull(message = "验证码不能为空")
    private Integer code;
}
