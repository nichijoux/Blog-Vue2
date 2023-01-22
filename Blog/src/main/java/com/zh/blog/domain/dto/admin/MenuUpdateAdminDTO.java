package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员修改菜单DTO", description = "管理员修改菜单DTO")
public class MenuUpdateAdminDTO {
    @ApiModelProperty("菜单id")
    @NotNull(message = "菜单id不能为空")
    @Min(value = 1, message = "菜单id最小为1")
    private Long id;

    @ApiModelProperty("父菜单id")
    @NotNull(message = "父菜单id不能为空")
    @Min(value = 0, message = "父菜单id最小为0")
    private Long pid;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("权限值")
    private String permissionValue;

    @ApiModelProperty("菜单类型（0：目录，1：菜单，2：按钮）")
    @Min(value = 0, message = "菜单类型（0：目录，1：菜单，2：按钮）")
    @Max(value = 2, message = "菜单类型（0：目录，1：菜单，2：按钮）")
    private Integer type;

    @ApiModelProperty("路由访问路径")
    private String path;

    @ApiModelProperty("vue组件路径")
    private String component;

    @ApiModelProperty("重定向地址")
    private String redirect;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("是否隐藏路由1（true）隐藏，0（false）不隐藏")
    private Boolean hidden;

    @ApiModelProperty("是否启用（1：启用，0：禁止）")
    private Boolean enable;

    @ApiModelProperty("菜单排序")
    @Min(value = 0, message = "排序最小值为0")
    private Integer sort;
}
