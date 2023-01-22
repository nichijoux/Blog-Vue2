package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "管理员评论列表VO", description = "管理员评论列表VO")
public class CommentListAdminVO {
    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("所属博客id")
    private Long articleId;

    @ApiModelProperty("所属文章标题")
    private String articleTitle;

    @ApiModelProperty("回复目标评论id,为-1则说明自身为根评论")
    private Long toCommentId;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
