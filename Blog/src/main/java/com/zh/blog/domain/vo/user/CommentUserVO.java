package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "普通用户查看到的评论对象VO", description = "普通用户查看到的评论对象VO")
public class CommentUserVO {
    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("根评论id")
    private Long rootId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("所属博客id")
    private Long articleId;

    @ApiModelProperty("回复目标评论id")
    private Long toCommentId;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建者的用户昵称")
    private String nickname;

    @ApiModelProperty("创建者的头像")
    private String avatar;

    @ApiModelProperty("子评论")
    private List<CommentUserVO> children;
}
