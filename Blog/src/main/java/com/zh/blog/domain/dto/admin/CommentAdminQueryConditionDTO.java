package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value = "管理员查询评论条件", description = "管理员查询评论条件")
public class CommentAdminQueryConditionDTO {
    @ApiModelProperty("文章标题")
    private Long articleTitle;

    @ApiModelProperty("评论类型")
    private Boolean type;

    @ApiModelProperty("创建开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createBeginTime;

    @ApiModelProperty("创建结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;
}
