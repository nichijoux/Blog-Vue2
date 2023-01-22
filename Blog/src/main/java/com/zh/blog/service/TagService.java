package com.zh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.dto.admin.TagAdminQueryConditionDTO;
import com.zh.blog.domain.entity.Tag;
import com.zh.blog.domain.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
public interface TagService extends IService<Tag> {

    /**
     * 获取所有启用的标签(仅包含id和name)
     *
     * @return 所有的标签列表
     */
    List<Tag> getAllEnableTag();

    /**
     * 管理员分页查询标签
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return PageVO
     */
    PageVO pageQueryTag(Long index, Long limit, TagAdminQueryConditionDTO queryConditionDTO);

    /**
     * 根据articleId获取tag列表(tag仅包含tagId,tagName)
     *
     * @param articleId 文章id
     * @return tag列表
     */
    List<Tag> getTagListByArticleId(Long articleId);

    /**
     * 根据tagId删除tag并且删除article-tag关系
     *
     * @param tagId 标签id
     */
    void deleteTag(Long tagId);

    /**
     * 统计已经启用的标签有多少
     *
     * @return 已经启用的标签的数据
     */
    Long countEnableTag();
}
