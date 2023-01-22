package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员选择标签时的VO", description = "管理员选择标签时的VO")
public class TagEnableAdminVO {
    @ApiModelProperty("tag的Id")
    private Long id;

    @ApiModelProperty("tag名称")
    private String name;
}
