package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户端显示的用户信息", description = "用户端显示的用户信息")
public class UserInfoVO {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("头像地址")
    private String avatar;
}
