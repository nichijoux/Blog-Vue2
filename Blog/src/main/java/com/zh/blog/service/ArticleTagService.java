package com.zh.blog.service;

import com.zh.blog.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 博客、标签中间表 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
public interface ArticleTagService extends IService<ArticleTag> {

    /**
     * 获取所有的文章id和标签id对
     *
     * @return 包含文章id, 标签id的ArticleTag列表
     */
    List<ArticleTag> getAllArticleTagId();

    /**
     * 根据tagId删除ArticleTag
     *
     * @param tagId 标签id
     */
    void deleteArticleTagByTagId(Long tagId);

    /**
     * 根据文章id删除ArticleTag
     *
     * @param articleId 文章id
     */
    void deleteArticleTagByArticleId(Long articleId);
}
