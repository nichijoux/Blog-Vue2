package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户端获取的统计数据", description = "用户端获取的统计数据")
public class StatisticsUserVO {
    @ApiModelProperty("发布文章数")
    private Long articleCount;

    @ApiModelProperty("启用标签数")
    private Long tagCount;

    @ApiModelProperty("用户评论数")
    private Long commentCount;

    @ApiModelProperty("文章总浏览数")
    private Long viewCount;
}
