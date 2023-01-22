package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value = "管理员查询文章条件", description = "管理员查询文章条件")
public class ArticleAdminQueryConditionDTO {
    @ApiModelProperty("标题名称")
    private String title;

    @ApiModelProperty("发表状态")
    private Boolean status;

    @ApiModelProperty("创建开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createBeginTime;

    @ApiModelProperty("创建结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;

    @ApiModelProperty("更新开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateBeginTime;

    @ApiModelProperty("更新结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateEndTime;
}
