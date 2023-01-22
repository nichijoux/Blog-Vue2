package com.zh.blog.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "按钮菜单", description = "按钮菜单")
public class ButtonMenu {
    @ApiModelProperty("父菜单id")
    private Long pid;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("权限值")
    private String permissionValue;

    @ApiModelProperty("菜单类型（0：目录，1：菜单，2：按钮）")
    private Integer type;

    @ApiModelProperty("是否启用（1：启用，0：禁止）")
    private Boolean enable;

    @ApiModelProperty("菜单排序")
    private Integer sort;
}
