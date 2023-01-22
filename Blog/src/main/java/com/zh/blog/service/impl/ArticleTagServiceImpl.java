package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.domain.entity.ArticleTag;
import com.zh.blog.mapper.ArticleTagMapper;
import com.zh.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 博客、标签中间表 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    /**
     * 获取所有的文章id和标签id对
     *
     * @return 包含文章id, 标签id的ArticleTag列表
     */
    @Override
    public List<ArticleTag> getAllArticleTagId() {
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        // 优化查询
        wrapper.select(ArticleTag::getArticleId, ArticleTag::getTagId);
        // 查询
        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据tagId删除ArticleTag
     *
     * @param tagId 标签id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticleTagByTagId(Long tagId) {
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(ArticleTag::getTagId, tagId);
        // 真正删除
        baseMapper.delete(wrapper);
    }

    /**
     * 根据文章id删除ArticleTag
     *
     * @param articleId 文章id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticleTagByArticleId(Long articleId) {
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(ArticleTag::getArticleId, articleId);
        // 删除
        baseMapper.delete(wrapper);
    }

}
