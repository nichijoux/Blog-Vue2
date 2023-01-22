package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "管理员标签列表VO", description = "管理员标签列表VO")
public class TagListAdminVO {
    @ApiModelProperty("tag的Id")
    private Long id;

    @ApiModelProperty("tag名称")
    private String name;

    @ApiModelProperty("tag备注描述")
    private String remark;

    @ApiModelProperty("tag是否启用")
    private Boolean enable;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
