package com.zh.blog.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "目录菜单", description = "目录菜单")
public class DirectoryMenu {
    @ApiModelProperty("父菜单id")
    private Long pid;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单类型（0：目录，1：菜单，2：按钮）")
    private Integer type;

    @ApiModelProperty("路由访问路径")
    private String path;

    @ApiModelProperty("重定向地址")
    private String redirect;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("是否隐藏路由1（true）隐藏，0（false）不隐藏")
    private Boolean hidden;

    @ApiModelProperty("是否启用（1：启用，0：禁止）")
    private Boolean enable;

    @ApiModelProperty("菜单排序")
    private Integer sort;
}
