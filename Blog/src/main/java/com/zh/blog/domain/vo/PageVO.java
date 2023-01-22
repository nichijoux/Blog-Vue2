package com.zh.blog.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页查询返回的VO对象", description = "分页查询返回的VO对象")
public class PageVO {
    @SuppressWarnings("rawtypes")
    @ApiModelProperty("数据")
    private List records;

    @ApiModelProperty("当前页")
    private Long current;

    @ApiModelProperty("总数据量")
    private Long total;

    @ApiModelProperty("是否有前一页")
    private Boolean hasPrevious;

    @ApiModelProperty("是否有后一页")
    private Boolean hasNext;
}
