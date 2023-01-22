package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户所看到的友链VO对象", description = "用户所看到的友链VO对象")
public class LinkUserVO {
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

    @ApiModelProperty("创建者头像")
    private String avatar;
}
