package com.zh.blog.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "管理员文章列表VO", description = "管理员文章列表VO")
public class ArticleListAdminVO {
    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("封面地址")
    private String cover;

    @ApiModelProperty("是否可评论，1为可评论，0为不可评论")
    private Boolean commentable;

    @ApiModelProperty("发布状态，1为发表，0为未发表")
    private Boolean status;

    @ApiModelProperty("是否推荐，1为推荐，0为不推荐")
    private Boolean recommend;

    @ApiModelProperty("浏览数量")
    private Integer viewCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("<标签id,标签名称>")
    private Map<Long, String> tagMap = new HashMap<>();
}
