package com.zh.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.blog.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
