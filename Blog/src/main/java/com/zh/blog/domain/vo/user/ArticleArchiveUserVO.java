package com.zh.blog.domain.vo.user;

import com.zh.blog.domain.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "文章归档VO", description = "文章归档VO")
public class ArticleArchiveUserVO {
    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("文章列表")
    private List<Article> articleList;
}
