package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "管理员更新用户信息DTO", description = "管理员更新用户信息DTO")
public class UserUpdateAdminDTO {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty("邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("是否启用，1启用，0禁用")
    private Boolean enable;
}
