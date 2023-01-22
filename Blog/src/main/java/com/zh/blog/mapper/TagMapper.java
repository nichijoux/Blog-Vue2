package com.zh.blog.mapper;

import com.zh.blog.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    // 根据articleId获取tag列表(tag仅包含tagId,tagName)
    List<Tag> selectTagListByArticleId(Long articleId);
}
