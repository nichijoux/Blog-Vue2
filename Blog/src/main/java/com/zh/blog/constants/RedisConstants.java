package com.zh.blog.constants;

public class RedisConstants {
    private RedisConstants() {
    }

    /**
     * 用户登录的redis Key
     */
    public static final String BLOG_USER_INFO_KEY = "blogUserInfo:";

    /**
     * 文章浏览数
     */
    public static final String ARTICLE_VIEW_COUNT = "articleViewCount:";

    /**
     * 热门文章列表
     */
    public static final String HOT_ARTICLE_LIST = "hotArticleList:";

    /**
     * 所有启用的tag
     */
    public static final String ALL_ENABLE_TAG = "allEnableTag:";

    /**
     * 邮箱验证码
     */
    public static final String VALIDATE_CODE = "registerValidateCode:";
}
