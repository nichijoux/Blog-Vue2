package com.zh.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "登录的DTO", description = "登录的DTO")
public class LoginDTO {
    @ApiModelProperty("账号")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 3, max = 20, message = "账号长度为3-20")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
