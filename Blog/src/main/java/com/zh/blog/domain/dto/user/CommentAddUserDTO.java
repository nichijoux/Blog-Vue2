package com.zh.blog.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "用户端添加评论DTO", description = "用户端添加评论DTO")
public class CommentAddUserDTO {
    @ApiModelProperty("文章id")
    @NotNull
    @Min(value = 1, message = "文章id错误")
    private Long articleId;

    @ApiModelProperty("根评论id,为-1则说明自身为根评论")
    @NotNull
    @Min(value = -1, message = "根评论id错误")
    private Long rootId;

    @ApiModelProperty("评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty("所回复的评论的userId,为-1则说明自身为根评论")
    @Min(value = -1, message = "回复目标评论用户错误")
    private Long toCommentUserId;

    @ApiModelProperty("回复目标评论id,为-1则说明自身为根评论")
    @Min(value = -1, message = "回复目标评论错误")
    private Long toCommentId;
}
