package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@ApiModel(value = "管理员查询友链条件", description = "管理员查询友链条件")
public class LinkAdminQueryConditionDTO {
    @ApiModelProperty("友链名称")
    private String name;

    @ApiModelProperty("友链描述")
    private String description;

    @ApiModelProperty("审核状态，0通过，1审核未通过，2未审核")
    @Min(value = 0, message = "审核状态，0通过，1审核未通过，2未审核")
    @Max(value = 2, message = "审核状态，0通过，1审核未通过，2未审核")
    private Integer status;
}
