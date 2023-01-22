package com.zh.blog.job;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.entity.Article;
import com.zh.blog.service.ArticleService;
import com.zh.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountJob {
    private ArticleService articleService;

    private RedisCache redisCache;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Scheduled(cron = "0/55 * * * * ?")
    public void updateViewCount() {
        // 获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConstants.ARTICLE_VIEW_COUNT);
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> {
                    Article article = new Article();
                    article.setId(Long.valueOf(entry.getKey()));
                    article.setViewCount(entry.getValue());
                    return article;
                })
                .collect(Collectors.toList());
        // 更新到数据库中
        articleService.updateBatchById(articleList);
    }
}
