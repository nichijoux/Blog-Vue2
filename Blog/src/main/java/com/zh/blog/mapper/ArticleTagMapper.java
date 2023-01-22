package com.zh.blog.mapper;

import com.zh.blog.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客、标签中间表 Mapper 接口
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
