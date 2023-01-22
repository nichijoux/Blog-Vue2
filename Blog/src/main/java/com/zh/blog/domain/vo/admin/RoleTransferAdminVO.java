package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员用于穿梭框的VO", description = "管理员用于穿梭框的VO")
public class RoleTransferAdminVO {
    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;
}
