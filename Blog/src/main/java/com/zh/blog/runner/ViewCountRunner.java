package com.zh.blog.runner;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.entity.Article;
import com.zh.blog.service.ArticleService;
import com.zh.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
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

    @Override
    public void run(String... args) {
        // 获取所有
        List<Article> articleList = articleService.list();
        Map<String, Integer> viewCountMap = articleList.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), Article::getViewCount));
        // 存储到redis
        redisCache.setCacheMap(RedisConstants.ARTICLE_VIEW_COUNT, viewCountMap);
    }
}
