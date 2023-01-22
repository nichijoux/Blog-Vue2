package com.zh.blog.controller.user;


import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.Result;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.user.ArticleArchiveUserVO;
import com.zh.blog.domain.vo.user.ArticleDetailUserVO;
import com.zh.blog.domain.vo.user.HotArticleUserVO;
import com.zh.blog.service.ArticleService;
import com.zh.blog.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Api(tags = "普通用户博客api中心")
@RestController
@RequestMapping("/user/article")
public class ArticleUserController {
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

    @ApiOperation(value = "获取热门文章(已发布)")
    @GetMapping("getHotArticleList")
    public Result getHotArticleList() {
        List<HotArticleUserVO> hotArticleVOList = articleService.getHotArticle();
        return Result.success(hotArticleVOList);
    }

    @ApiOperation(value = "获取文章归档")
    @GetMapping("getArchiveList")
    public Result getArchiveList() {
        List<ArticleArchiveUserVO> articleArchiveUserVOList = articleService.getArchiveList();
        return Result.success(articleArchiveUserVOList);
    }

    @ApiOperation(value = "分页查询,获取文章(已发布)")
    @GetMapping(value = "pageQueryPublishedArticle/{index}/{limit}")
    public Result pageQueryPublishedArticle(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "tagId", value = "标签Id")
            @RequestParam(value = "tagId", required = false) Long tagId) {
        PageVO pageVO = articleService.pageQueryPublishedArticle(index, limit, tagId);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "获取文章(已发布)详情")
    @GetMapping("getPublishedArticleDetail/{articleId}")
    public Result getPublishedArticleDetail(
            @ApiParam(value = "articleId", name = "要查询的文章id", required = true)
            @PathVariable Long articleId) {
        ArticleDetailUserVO articleDetailVO = articleService.getPublishedArticleDetail(articleId);
        // 更新浏览数
        redisCache.incrementCacheMapValue(RedisConstants.ARTICLE_VIEW_COUNT, articleId.toString(), 1);
        return Result.success(articleDetailVO);
    }
}

