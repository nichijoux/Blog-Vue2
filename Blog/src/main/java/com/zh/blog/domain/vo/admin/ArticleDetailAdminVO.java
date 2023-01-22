package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "管理员显示的文章详情VO对象", description = "管理员显示的文章详情VO对象")
public class ArticleDetailAdminVO {
    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("封面地址")
    private String cover;

    @ApiModelProperty("是否可评论，1为可评论，0为不可评论")
    private Boolean commentable;

    @ApiModelProperty("发布状态，1为发表，0为未发表")
    private Boolean status;

    @ApiModelProperty("是否推荐，1为推荐，0为不推荐")
    private Boolean recommend;

    @ApiModelProperty("文章对应的标签名列表")
    private List<Long> tagIdList;
}
