package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员查询角色条件", description = "管理员查询角色条件")
public class RoleAdminQueryConditionDTO {
    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色权限字符串")
    private String key;

    @ApiModelProperty("是否启用角色")
    private Boolean enable;
}
