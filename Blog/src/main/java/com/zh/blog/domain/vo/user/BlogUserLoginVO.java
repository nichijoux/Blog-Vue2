package com.zh.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "普通用户登录的VO", description = "普通用户登录的VO")
public class BlogUserLoginVO {
    @ApiModelProperty("jwt生成的token")
    private String token;

    @ApiModelProperty("前台要显示的用户信息")
    private UserInfoVO userInfo;
}
