package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "用户显示的文章详情VO对象", description = "用户显示的文章详情VO对象")
public class ArticleDetailUserVO {
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

    @ApiModelProperty("浏览数量")
    private Integer viewCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("文章对应的标签名列表")
    private List<TagUserVO> tagList;
}
