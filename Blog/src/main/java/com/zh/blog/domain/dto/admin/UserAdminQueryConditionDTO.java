package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员查询用户条件", description = "管理员查询用户条件")
public class UserAdminQueryConditionDTO {
    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("是否启用")
    private Boolean enable;

    @ApiModelProperty("用户类型,0为普通用户,1为管理员")
    private Boolean type;
}
