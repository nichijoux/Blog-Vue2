package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员添加用户DTO", description = "管理员添加用户DTO")
public class UserAddAdminDTO {
    @ApiModelProperty("用户账号")
    @NotBlank(message = "用户账号不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty("邮箱")
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("是否启用，1启用，0禁用")
    private Boolean enable;
}
