package com.zh.blog.domain.vo.admin;

import com.zh.blog.domain.vo.user.UserInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "管理员显示的用户信息", description = "管理员显示的用户信息")
public class AdminUserInfoVO {
    @ApiModelProperty("角色列表")
    private List<String> roleList;

    @ApiModelProperty("权限值列表")
    private List<String> permissionList;

    @ApiModelProperty("用户信息")
    private UserInfoVO userInfoVO;
}
