package com.zh.blog.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel(value = "管理员修改文章的DTO", description = "管理员修改文章的DTO")
public class ArticleUpdateAdminDTO {
    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章标题")
    @NotBlank(message = "文章标题不能为空")
    @Size(min = 3, max = 30, message = "文章标题长度应该在3-30之间")
    private String title;

    @ApiModelProperty("文章摘要")
    @NotBlank(message = "文章摘要不能为空")
    @Size(min = 3, max = 50, message = "文章摘要长度应该在3-30之间")
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
