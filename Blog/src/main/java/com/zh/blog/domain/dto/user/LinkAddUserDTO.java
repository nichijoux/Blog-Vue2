package com.zh.blog.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "添加友链DTO", description = "添加友链DTO")
public class LinkAddUserDTO {
    @NotBlank(message = "友链名称不能为空")
    @ApiModelProperty("友链名称")
    private String name;

    @NotBlank(message = "友链logo不能为空")
    @ApiModelProperty("友链logo")
    private String logo;

    @NotBlank(message = "友链地址不能为空")
    @ApiModelProperty("友链地址")
    private String address;

    @NotBlank(message = "友链描述不能为空")
    @ApiModelProperty("友链描述")
    private String description;
}
