package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "标签VO对象", description = "标签VO对象")
public class TagUserVO {
    @ApiModelProperty("标签id")
    private Long id;

    @ApiModelProperty("标签名")
    private String name;
}
