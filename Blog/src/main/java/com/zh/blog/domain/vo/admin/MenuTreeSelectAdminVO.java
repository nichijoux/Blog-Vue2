package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "管理员TreeSelect组件所需VO", description = "管理员TreeSelect组件所需VO")
public class MenuTreeSelectAdminVO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("父菜单id")
    private Long pid;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("子菜单列表")
    private List<MenuTreeSelectAdminVO> children;
}
