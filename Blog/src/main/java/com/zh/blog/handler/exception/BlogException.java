package com.zh.blog.handler.exception;

import com.zh.blog.enums.ResultErrorEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类抛出异常处理类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BlogException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示消息")
    private String message;

    public BlogException(String message) {
        this.code = 20001;
        this.message = message;
    }

    public BlogException(ResultErrorEnum error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}