package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "管理员添加角色DTO", description = "管理员添加角色DTO")
public class RoleAddAdminDTO {
    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty("角色权限字符串")
    @NotBlank(message = "角色权限字符串不能为空")
    private String key;

    @ApiModelProperty("角色备注")
    private String remark;

    @ApiModelProperty("显示顺序")
    @Min(value = 0, message = "排序最小值为0")
    private Integer sort;

    @ApiModelProperty("是否启用，1启用，0禁用")
    private Boolean enable;
}
