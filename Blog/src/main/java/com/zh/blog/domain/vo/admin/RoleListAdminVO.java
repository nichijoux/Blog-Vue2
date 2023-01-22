package com.zh.blog.domain.vo.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员分页查看角色列表VO", description = "管理员分页查看角色列表VO")
public class RoleListAdminVO {
    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色权限字符串")
    private String key;

    @ApiModelProperty("角色备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("显示顺序")
    private Integer sort;

    @ApiModelProperty("是否启用，1启用，0禁用")
    private Boolean enable;
}
