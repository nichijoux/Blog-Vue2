package com.zh.blog.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "更新用户信息的DTO", description = "更新用户信息的DTO")
public class UserUpdateUserDTO {
    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("头像地址")
    @NotBlank(message = "头像地址错误")
    private String avatar;
}
