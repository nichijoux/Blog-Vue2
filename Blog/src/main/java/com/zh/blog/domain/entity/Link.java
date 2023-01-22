package com.zh.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Getter
@Setter
@TableName("link")
@ApiModel(value = "Link对象", description = "友链对象")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("友链主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("友链名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("友链logo")
    @TableField("logo")
    private String logo;

    @ApiModelProperty("友链地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("友链描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("审核状态，0通过，1审核未通过，2未审核")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新者")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("逻辑删除，1删除，0未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;
}
