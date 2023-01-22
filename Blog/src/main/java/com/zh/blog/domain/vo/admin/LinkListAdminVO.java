package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ApiModel(value = "管理员分页查看友链VO", description = "管理员分页查看友链VO")
public class LinkListAdminVO {
    @ApiModelProperty("友链主键")
    private Long id;

    @ApiModelProperty("友链名称")
    private String name;

    @ApiModelProperty("友链logo")
    private String logo;

    @ApiModelProperty("友链地址")
    private String address;

    @ApiModelProperty("友链描述")
    private String description;

    @ApiModelProperty("审核状态，0通过，1审核未通过，2未审核")
    private Integer status;
}
