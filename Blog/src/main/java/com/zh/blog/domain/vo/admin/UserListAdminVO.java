package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员用户列表VO", description = "管理员用户列表VO")
public class UserListAdminVO {
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

    @ApiModelProperty("用户类型，0为普通用户，1为管理员")
    private Boolean type;

    @ApiModelProperty("是否启用，1启用，0禁用")
    private Boolean enable;
}
