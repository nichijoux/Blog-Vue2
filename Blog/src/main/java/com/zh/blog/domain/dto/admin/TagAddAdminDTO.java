package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "管理员添加标签DTO", description = "管理员添加标签DTO")
public class TagAddAdminDTO {
    @ApiModelProperty("标签名")
    @NotBlank(message = "标签名不能为空")
    private String name;

    @ApiModelProperty("标签备注")
    @NotBlank(message = "标签备注不能为空")
    private String remark;

    @ApiModelProperty("是否启用（1：启用，0：禁止）")
    private Boolean enable;
}
