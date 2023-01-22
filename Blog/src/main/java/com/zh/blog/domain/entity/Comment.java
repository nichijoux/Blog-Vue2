package com.zh.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Getter
@Setter
@TableName("comment")
@ApiModel(value = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("根评论id,为-1则说明自身为根评论")
    @TableField("root_id")
    private Long rootId;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("所属博客id")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty("所回复的评论的userId,为-1则说明自身为根评论")
    @TableField("to_comment_user_id")
    private Long toCommentUserId;

    @ApiModelProperty("回复目标评论id,为-1则说明自身为根评论")
    @TableField("to_comment_id")
    private Long toCommentId;

    @ApiModelProperty("创建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新者")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("逻辑删除，1删除，0未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;
}
