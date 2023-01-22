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
@TableName("menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("父菜单id")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty("菜单名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("权限值")
    @TableField("permission_value")
    private String permissionValue;

    @ApiModelProperty("菜单类型（0：目录，1：菜单，2：按钮）")
    @TableField("`type`")
    private Integer type;

    @ApiModelProperty("路由访问路径")
    @TableField("path")
    private String path;

    @ApiModelProperty("vue组件路径")
    @TableField("component")
    private String component;

    @ApiModelProperty("重定向地址")
    @TableField("redirect")
    private String redirect;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("是否隐藏路由1（true）隐藏，0（false）不隐藏")
    @TableField("hidden")
    private Boolean hidden;

    @ApiModelProperty("是否启用（1：启用，0：禁止）")
    @TableField("is_enable")
    private Boolean enable;

    @ApiModelProperty("菜单排序")
    @TableField("sort")
    private Integer sort;

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
