package com.zh.blog.service.impl;

import com.zh.blog.domain.vo.user.StatisticsUserVO;
import com.zh.blog.service.ArticleService;
import com.zh.blog.service.CommentService;
import com.zh.blog.service.StatisticsService;
import com.zh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private ArticleService articleService;

    private TagService tagService;

    private CommentService commentService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 获取用户端可见的统计数据(发布文章数,可用标签数,评论数,浏览数)
     *
     * @return 统计数据
     */
    @Override
    public StatisticsUserVO getBlogUserStatistics() {
        // 发布文章数
        Long articleCount = articleService.countPublishedArticle();
        // 启用标签数
        Long tagCount = tagService.countEnableTag();
        // 发布文章的评论数
        Long commentCount = commentService.countPublishedArticleComment();
        // 发布文章的浏览数据
        Long viewCount = articleService.countPublishedArticleView();
        return new StatisticsUserVO(articleCount, tagCount, commentCount, viewCount);
    }
}
