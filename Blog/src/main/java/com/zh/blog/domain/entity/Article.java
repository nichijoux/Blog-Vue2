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
 * 博客表
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Getter
@Setter
@TableName("article")
@ApiModel(value = "Article对象", description = "博客表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("文章标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("文章摘要")
    @TableField("summary")
    private String summary;

    @ApiModelProperty("文章内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("封面地址")
    @TableField("cover")
    private String cover;

    @ApiModelProperty("是否可评论，1为可评论，0为不可评论")
    @TableField("is_commentable")
    private Boolean commentable;

    @ApiModelProperty("发布状态，1为发表，0为未发表")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("是否推荐，1为推荐，0为不推荐")
    @TableField("is_recommend")
    private Boolean recommend;

    @ApiModelProperty("浏览数量")
    @TableField("view_count")
    private Integer viewCount;

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
    @TableField(value = "is_deleted")
    @TableLogic
    private Boolean deleted;
}
