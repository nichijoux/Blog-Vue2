package com.zh.blog.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultErrorEnum {
    LOGIN_FAILURE(20002, "用户账号或密码错误"),
    TOKEN_ERROR_LOGIN_AGAIN(20003, "token出现错误,请重新登录"),
    OPERATION_NEED_LOGIN(20004, "该操作需要登录"),
    COMMENT_ORDER_TYPE_ERROR(20005, "评论排序类型错误"),
    REGISTER_FAILURE(20006, "注册失败"),
    REGISTER_ACCOUNT_EXIST(20007, "用户账号已经存在"),
    REGISTER_EMAIL_EXIST(20008, "邮箱已经存在"),
    ADD_TAG_FAILURE(20009, "添加标签失败"),
    UPDATE_TAG_FAILURE(20010, "更新标签失败"),
    DELETE_TAG_FAILURE(20011, "删除标签失败"),
    ARTICLE_NOT_EXIST(30000, "查询文章不存在"),
    ARTICLE_STATUS_ERROR(30001, "查询文章状态异常"),
    NO_OPERATOR_PERMISSION(40000, "没有操作权限"),
    AUTHENTICATION_OR_AUTHORIZATION_ERROR(40001, "认证或授权错误"),
    USER_ROLE_IS_EMPTY(40002, "用户角色不存在,或者用户角色全部被禁用"),
    MENU_EXITS_CHILD_DELETE_FAILURE(40003, "菜单删除失败,因为存在子菜单"),
    USER_IS_USING_DELETE_FAILURE(40004, "当前用户正在使用,用户删除失败"),
    USER_CANT_BE_DELETE(40005, "该用户不能被删除"),
    USER_IS_USING_CHANGE_FAILURE(40006, "当前用户正在使用,无法禁启用"),
    USER_CANT_BE_CHANGE(40007, "该用户状态无法被改变"),
    BUTTON_MENU_CANT_BE_PARENT(40008, "按钮菜单不能作为父菜单"),
    MENU_TYPE_ERROR(40009, "菜单类型错误"),
    ADD_MENU_FAILURE(40010, "添加菜单失败"),
    UPDATE_MENU_FAILURE(40011, "更新菜单失败"),
    MENU_ID_PID_ERROR(40012, "更新菜单失败,上级菜单不能选择自己");

    private Integer code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
