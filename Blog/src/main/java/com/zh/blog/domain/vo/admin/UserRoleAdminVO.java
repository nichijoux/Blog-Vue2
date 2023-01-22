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
@ApiModel(value = "用户角色VO", description = "用户角色VO")
public class UserRoleAdminVO {
    @ApiModelProperty("用户分配的角色id列表")
    private List<Long> assignRoleIdList;

    @ApiModelProperty("所有的角色列表")
    private List<RoleTransferAdminVO> roleList;
}
