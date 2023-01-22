package com.zh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.dto.admin.TagAdminQueryConditionDTO;
import com.zh.blog.domain.entity.Tag;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.TagListAdminVO;
import com.zh.blog.mapper.TagMapper;
import com.zh.blog.service.ArticleTagService;
import com.zh.blog.service.TagService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private ArticleTagService articleTagService;

    private RedisCache redisCache;

    @Autowired
    public void setArticleTagService(ArticleTagService articleTagService) {
        this.articleTagService = articleTagService;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 获取所有启用的标签(仅包含id和name)
     *
     * @return 所有的标签列表
     */
    @Override
    public List<Tag> getAllEnableTag() {
        List<Tag> cacheList = JSON.parseArray(
                redisCache.getCacheObject(RedisConstants.ALL_ENABLE_TAG),
                Tag.class);
        if (Objects.nonNull(cacheList) && !cacheList.isEmpty()) {
            return cacheList;
        }
        // sql查询
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        // 优化查询
        wrapper.select(Tag::getId, Tag::getName);
        // 设置条件
        wrapper.eq(Tag::getEnable, true);
        // 查询数据
        List<Tag> tagList = baseMapper.selectList(wrapper);
        redisCache.setCacheObject(RedisConstants.ALL_ENABLE_TAG,
                JSON.toJSON(tagList).toString());
        return tagList;
    }

    /**
     * 管理员分页查询标签
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return PageVO
     */
    @Override
    public PageVO pageQueryTag(Long index, Long limit, TagAdminQueryConditionDTO queryConditionDTO) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Tag::getId, Tag::getName, Tag::getRemark, Tag::getEnable,
                Tag::getCreateTime, Tag::getUpdateTime);
        // 设置排序
        wrapper.orderByDesc(Tag::getCreateTime);
        // 设置查询条件
        if (Objects.nonNull(queryConditionDTO)) {
            // 设置标签
            String name = queryConditionDTO.getName();
            wrapper.like(Objects.nonNull(name), Tag::getName, name);
            // 设置备注
            String remark = queryConditionDTO.getRemark();
            wrapper.like(Objects.nonNull(remark), Tag::getRemark, remark);
            // 是否启用
            Boolean enable = queryConditionDTO.getEnable();
            wrapper.eq(Objects.nonNull(enable), Tag::getEnable, enable);
            // 设置创建时间
            Date createBeginTime = queryConditionDTO.getCreateBeginTime();
            Date createEndTime = queryConditionDTO.getCreateEndTime();
            wrapper.ge(Objects.nonNull(createBeginTime), Tag::getCreateTime, createBeginTime);
            wrapper.le(Objects.nonNull(createEndTime), Tag::getCreateTime, createEndTime);
            // 设置更新时间
            Date updateBeginTime = queryConditionDTO.getUpdateBeginTime();
            Date updateEndTime = queryConditionDTO.getUpdateEndTime();
            wrapper.ge(Objects.nonNull(updateBeginTime), Tag::getUpdateTime, updateBeginTime);
            wrapper.le(Objects.nonNull(updateEndTime), Tag::getUpdateTime, updateEndTime);
        }
        // 分页查询
        Page<Tag> tagPage = new Page<>(index, limit);
        baseMapper.selectPage(tagPage, wrapper);
        List<TagListAdminVO> tagListAdminVOList = BeanCopyUtils.copyBeanList(tagPage.getRecords(), TagListAdminVO.class);
        return BeanCopyUtils.copyPageVO(tagPage, tagListAdminVOList);
    }

    /**
     * 根据articleId获取tag列表(tag仅包含tagId,tagName)
     *
     * @param articleId 文章id
     * @return tag列表
     */
    @Override
    public List<Tag> getTagListByArticleId(Long articleId) {
        return baseMapper.selectTagListByArticleId(articleId);
    }

    /**
     * 根据tagId删除tag并且删除article-tag
     *
     * @param tagId 标签id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTag(Long tagId) {
        // 删除article-tag
        articleTagService.deleteArticleTagByTagId(tagId);
        // 删除tag本体
        baseMapper.deleteById(tagId);
    }

    /**
     * 统计已经启用的标签有多少
     *
     * @return 已经启用的标签的数据
     */
    @Override
    public Long countEnableTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        // 设置条件
        wrapper.eq(Tag::getEnable, true);
        // 统计
        return baseMapper.selectCount(wrapper);
    }
}
