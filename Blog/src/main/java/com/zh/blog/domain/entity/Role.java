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
 * @since 2022-11-14
 */
@Getter
@Setter
@TableName("role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("角色名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("角色权限字符串")
    @TableField("`key`")
    private String key;

    @ApiModelProperty("角色备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("是否启用，1启用，0禁用")
    @TableField("is_enable")
    private Boolean enable;

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
