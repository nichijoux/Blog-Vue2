package com.zh.blog.mapper;

import com.zh.blog.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获取发布文章的评论数量
     *
     * @return 发布文章的评论数量
     */
    Long countPublishedArticleComment();
}
