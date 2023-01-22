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
@ApiModel(value = "角色具有的菜单VO", description = "角色具有的菜单VO")
public class MenuRoleAdminVO {
    @ApiModelProperty("角色拥有的菜单的id列表")
    private List<Long> selectMenuIdList;

    @ApiModelProperty("所有的菜单")
    private List<MenuTreeSelectAdminVO> menuTree;
}
