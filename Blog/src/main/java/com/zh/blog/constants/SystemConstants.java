package com.zh.blog.constants;

public class SystemConstants {

    private SystemConstants() {
    }

    /**
     * 文章发布的常量
     */
    public static final boolean PUBLISHED_STATUS = true;

    /**
     * 审核通过的友链的常量
     */
    public static final int AUDIT_PASSED_LINK = 0;

    /**
     * 审核失败的友链的常量
     */
    public static final int AUDIT_FAILED_LINK = 1;

    /**
     * 等待审核的友链的常量
     */
    public static final int AUDIT_WAIT_LINK = 2;

    /**
     * 普通用户的常量
     */
    public static final boolean NORMAL_USER_TYPE = false;

    /**
     * 管理员用户的常量
     */
    public static final boolean ADMIN_USER_TYPE = true;

    /**
     * 评论为根评论时,rootId和toCommentId应该为的值
     */
    public static final long COMMENT_IS_ROOT_ID = -1L;

    /**
     * 评论正常
     */
    public static final int COMMENT_STATUS_NORMAL = 0;

    /**
     * 评论被举报
     */
    public static final int COMMENT_STATUS_BE_REPORTED = 1;

    /**
     * 评论根据创建时间升序排序
     */
    public static final int COMMENT_ORDER_BY_TIME_ASC = 0;

    /**
     * 评论根据创建时间降序排序
     */
    public static final int COMMENT_ORDER_BY_TIME_DESC = 1;

    /**
     * 菜单类型为目录
     */
    public static final int MENU_TYPE_DIRECTORY = 0;

    /**
     * 菜单类型为menu
     */
    public static final int MENU_TYPE_MENU = 1;

    /**
     * 菜单类型为button
     */
    public static final int MENU_TYPE_BUTTON = 2;

    /**
     * menu tree的根节点id
     */
    public static final long MENU_TREE_ROOT = 0;

    /**
     * 超级管理员的id
     */
    public static final long SUPER_ADMIN_ID = 0;
}
