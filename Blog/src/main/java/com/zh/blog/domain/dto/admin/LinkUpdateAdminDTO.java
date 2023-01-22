package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "管理员修改友链的DTO", description = "管理员修改友链的DTO")
public class LinkUpdateAdminDTO {
    @ApiModelProperty("友链id")
    private Long id;

    @ApiModelProperty("友链名称")
    @NotBlank(message = "友链名不能为空")
    private String name;

    @ApiModelProperty("友链logo")
    private String logo;

    @ApiModelProperty("友链地址")
    private String address;

    @ApiModelProperty("友链描述")
    @NotBlank(message = "友链描述不能为空")
    @Size(min = 3, message = "友链描述最少为3个字符")
    private String description;

    @ApiModelProperty("审核状态，0通过，1审核未通过，2未审核")
    @Min(value = 0, message = "审核状态范围最小为0(审核通过)")
    @Max(value = 2, message = "审核状态范围最大为2(未审核)")
    private Integer status;
}
