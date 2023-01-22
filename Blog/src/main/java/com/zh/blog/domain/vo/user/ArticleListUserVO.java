package com.zh.blog.domain.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "用户分页查询显示的文章列表VO", description = "用户分页查询显示的文章列表VO")
public class ArticleListUserVO {
    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("封面地址")
    private String cover;

    @ApiModelProperty("浏览数量")
    private Integer viewCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人id")
    @JsonIgnore
    private Long createBy;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("标签列表")
    private List<TagUserVO> tagList = new ArrayList<>();
}
