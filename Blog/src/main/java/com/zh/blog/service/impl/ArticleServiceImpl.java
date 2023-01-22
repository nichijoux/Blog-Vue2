package com.zh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.constants.RedisConstants;
import com.zh.blog.constants.SystemConstants;
import com.zh.blog.domain.dto.admin.ArticleAddAdminDTO;
import com.zh.blog.domain.dto.admin.ArticleAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.ArticleUpdateAdminDTO;
import com.zh.blog.domain.entity.Article;
import com.zh.blog.domain.entity.ArticleTag;
import com.zh.blog.domain.entity.Tag;
import com.zh.blog.domain.entity.User;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.ArticleDetailAdminVO;
import com.zh.blog.domain.vo.admin.ArticleListAdminVO;
import com.zh.blog.domain.vo.user.*;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.mapper.ArticleMapper;
import com.zh.blog.service.ArticleService;
import com.zh.blog.service.ArticleTagService;
import com.zh.blog.service.TagService;
import com.zh.blog.service.UserService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private ArticleTagService articleTagService;

    private TagService tagService;

    private UserService userService;

    private RedisCache redisCache;

    @Autowired
    public void setArticleTagService(ArticleTagService articleTagService) {
        this.articleTagService = articleTagService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 获取热门文章
     *
     * @return 热门文章列表
     */
    @Override
    public List<HotArticleUserVO> getHotArticle() {
        List<HotArticleUserVO> cacheList = JSON.parseArray(
                redisCache.getCacheObject(RedisConstants.HOT_ARTICLE_LIST),
                HotArticleUserVO.class);
        if (Objects.nonNull(cacheList) && !cacheList.isEmpty()) {
            // redis中有数据
            return cacheList;
        }
        // 查询数据
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 根据VO设置查询列
        wrapper.select(Article::getId, Article::getTitle, Article::getViewCount);
        // 查询已发表文章
        wrapper.eq(Article::getStatus, true);
        // 设置为推荐
        wrapper.eq(Article::getRecommend, true);
        // 按浏览量排序
        wrapper.orderByDesc(Article::getViewCount);
        // 只要最多10篇
        Page<Article> articlePage = new Page<>(1, 10);
        baseMapper.selectPage(articlePage, wrapper);
        // 获取文章
        List<Article> articleList = articlePage.getRecords();
        List<HotArticleUserVO> hotArticleUserVOList = BeanCopyUtils.copyBeanList(articleList, HotArticleUserVO.class);
        // 设置redis缓存
        redisCache.setCacheObject(RedisConstants.HOT_ARTICLE_LIST,
                JSON.toJSON(hotArticleUserVOList).toString());
        return hotArticleUserVOList;
    }

    /**
     * 用户端根据index,limit,tagId分页查询文章列表
     *
     * @param index 当前页
     * @param limit 每页记录数
     * @param tagId 标签id,可省略
     * @return 封装后的ListVO
     */
    @Override
    public PageVO pageQueryPublishedArticle(Long index, Long limit, Long tagId) {
        // 查询所有的articleIdList
        List<ArticleTag> articleTagList = articleTagService.getAllArticleTagId();
        // 查询所有的tagList
        List<Tag> tagList = tagService.getAllEnableTag();
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 根据VO查询优化
        wrapper.select(Article::getId, Article::getTitle, Article::getSummary,
                Article::getCover, Article::getViewCount,
                Article::getCreateTime, Article::getCreateBy);
        // 设置查询条件
        wrapper.eq(Article::getStatus, true);
        // 判断tagId是否需要
        if (Objects.nonNull(tagId)) {
            List<Long> articleIdList = articleTagList.stream().filter(at -> at.getTagId().equals(tagId))
                    .map(ArticleTag::getArticleId)
                    .collect(Collectors.toList());
            wrapper.in(articleIdList.size() > 0, Article::getId, articleIdList);
        }
        // 设置排序
        wrapper.orderByDesc(Article::getUpdateTime);
        // 分页查询
        Page<Article> articlePage = new Page<>(index, limit);
        baseMapper.selectPage(articlePage, wrapper);
        // 查询admin用户(可以写博文的用户)
        List<User> userList = userService.getAllAdminUser();
        Map<Long, String> adminNicknameMap = userList.stream().collect(Collectors.toMap(User::getId, User::getNickname));
        // 查询完毕后将tag和author封装到结果中
        List<ArticleListUserVO> articleList = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListUserVO.class);
        articleList.forEach(article -> {
            // 获取tagIdList
            List<Long> tagIdList = articleTagList.stream().filter(at -> Objects.equals(article.getId(), at.getArticleId()))
                    .map(ArticleTag::getId)
                    .collect(Collectors.toList());
            // 获取TagUserVO
            List<TagUserVO> tagUserVOList = tagList.stream().filter(tag -> tagIdList.contains(tag.getId()))
                    .map(tag -> {
                        TagUserVO tagUserVO = new TagUserVO();
                        tagUserVO.setId(tag.getId());
                        tagUserVO.setName(tag.getName());
                        return tagUserVO;
                    })
                    .collect(Collectors.toList());
            article.setTagList(tagUserVOList);
            // 设置author
            article.setAuthor(adminNicknameMap.get(article.getCreateBy()));
        });
        return BeanCopyUtils.copyPageVO(articlePage, articleList);
    }

    /**
     * 根据文章id查询文章详情(2次mysql查询)
     *
     * @param articleId 文章id
     * @return 前台展示时的文章VO对象
     */
    @Override
    public ArticleDetailUserVO getPublishedArticleDetail(Long articleId) {
        // 根据id查询文章
        Article article = baseMapper.selectById(articleId);
        if (article == null) {
            throw new BlogException(ResultErrorEnum.ARTICLE_NOT_EXIST);
        }
        if (!article.getStatus().equals(SystemConstants.PUBLISHED_STATUS)) {
            throw new BlogException(ResultErrorEnum.ARTICLE_STATUS_ERROR);
        }
        // 根据文章id查询标签id和标签name
        List<Tag> tagList = tagService.getTagListByArticleId(article.getId());
        // 封装为VO变量
        ArticleDetailUserVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailUserVO.class);
        // 添加tag标签
        if (Objects.nonNull(tagList)) {
            List<TagUserVO> tagUserVOList = tagList.stream().map(tag -> {
                TagUserVO tagUserVO = new TagUserVO();
                tagUserVO.setId(tag.getId());
                tagUserVO.setName(tag.getName());
                return tagUserVO;
            }).collect(Collectors.toList());
            // 设置tag
            articleDetailVO.setTagList(tagUserVOList);
        }
        return articleDetailVO;
    }

    /**
     * 管理员根据index,limit,queryConditionDTO查询文章列表
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 封装后的PageVO
     */
    @Override
    public PageVO pageQueryArticle(Long index, Long limit, ArticleAdminQueryConditionDTO queryConditionDTO) {
        // 查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 根据VO查询优化
        wrapper.select(Article::getId, Article::getTitle, Article::getSummary, Article::getCover,
                Article::getCommentable, Article::getStatus, Article::getRecommend, Article::getViewCount,
                Article::getCreateTime, Article::getUpdateTime);
        // 设置排序
        wrapper.orderByDesc(Article::getCreateTime);
        // 设置查询条件
        if (Objects.nonNull(queryConditionDTO)) {
            // 标题设置
            String title = queryConditionDTO.getTitle();
            wrapper.like(Objects.nonNull(title), Article::getTitle, title);
            // 发表状态
            Boolean status = queryConditionDTO.getStatus();
            wrapper.eq(Objects.nonNull(status), Article::getStatus, status);
            // 创建时间
            Date createBeginDate = queryConditionDTO.getCreateBeginTime();
            Date createEndDate = queryConditionDTO.getCreateEndTime();
            wrapper.ge(Objects.nonNull(createBeginDate), Article::getCreateTime, createBeginDate);
            wrapper.le(Objects.nonNull(createEndDate), Article::getCreateTime, createEndDate);
            // 更新时间
            Date updateBeginDate = queryConditionDTO.getUpdateBeginTime();
            Date updateEndDate = queryConditionDTO.getUpdateEndTime();
            wrapper.ge(Objects.nonNull(updateBeginDate), Article::getUpdateTime, updateBeginDate);
            wrapper.le(Objects.nonNull(updateEndDate), Article::getUpdateTime, updateEndDate);
        }
        // 分页查询
        Page<Article> articlePage = new Page<>(index, limit);
        baseMapper.selectPage(articlePage, wrapper);
        List<ArticleListAdminVO> articleListAdminVOList = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListAdminVO.class);
        // 查询tag标签(直接获取所有的tag和articleTag)
        // 查询所有的articleTag
        List<ArticleTag> articleTagList = articleTagService.getAllArticleTagId();
        // 封装为Map<Long,List<Long>>
        Map<Long, List<Long>> articleTagIdMap = articleTagList.stream().collect(Collectors.toMap(ArticleTag::getArticleId,
                articleTag -> {
                    List<Long> tagIdList = new ArrayList<>();
                    tagIdList.add(articleTag.getTagId());
                    return tagIdList;
                },
                (List<Long> newValueList, List<Long> oldValueList) -> {
                    newValueList.addAll(oldValueList);
                    return newValueList;
                }));
        // 查询所有启用的tag
        List<Tag> tagList = tagService.getAllEnableTag();
        Map<Long, String> tagNameMap = tagList.stream().collect(Collectors.toMap(Tag::getId, Tag::getName));
        // 将标签封装到articleListAdminVO中
        articleListAdminVOList.forEach(articleListAdminVO -> {
            // 获取标签id列表
            List<Long> tagIdList = articleTagIdMap.get(articleListAdminVO.getId());
            if (Objects.nonNull(tagIdList) && !tagIdList.isEmpty()) {
                Map<Long, String> tagMap = articleListAdminVO.getTagMap();
                // 遍历添加到tagMap中
                tagIdList.forEach(tagId -> tagMap.put(tagId, tagNameMap.get(tagId)));
            }
        });
        return BeanCopyUtils.copyPageVO(articlePage, articleListAdminVOList);
    }

    /**
     * 根据文章id删除文章,及文章-标签关系
     *
     * @param articleId 要删除的文章id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticle(Long articleId) {
        // 删除文章-标签关系
        articleTagService.deleteArticleTagByArticleId(articleId);
        // 删除文章
        baseMapper.deleteById(articleId);
    }

    /**
     * 根据文章id获取文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    @Override
    public ArticleDetailAdminVO getArticleDetail(Long articleId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Article::getId, Article::getTitle, Article::getSummary,
                Article::getContent, Article::getCover, Article::getCommentable,
                Article::getStatus, Article::getRecommend);
        // 设置查询条件
        wrapper.eq(Article::getId, articleId);
        wrapper.last("limit 1");
        // 查询
        Article article = baseMapper.selectOne(wrapper);
        ArticleDetailAdminVO detailAdminVO = BeanCopyUtils.copyBean(article, ArticleDetailAdminVO.class);
        // 查询article的标签
        List<Tag> tagList = tagService.getTagListByArticleId(articleId);
        detailAdminVO.setTagIdList(tagList.stream().map(Tag::getId)
                .collect(Collectors.toList()));
        return detailAdminVO;
    }

    /**
     * 更新文章(包括标签-文章信息)
     *
     * @param updateAdminDTO 更新信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticle(ArticleUpdateAdminDTO updateAdminDTO) {
        // 更新article-tag信息
        articleTagService.deleteArticleTagByArticleId(updateAdminDTO.getId());
        // 添加信息
        Optional.ofNullable(updateAdminDTO.getTagIdList()).ifPresent(tagIdList -> {
            List<ArticleTag> articleTagList = tagIdList.stream().map(tagId -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tagId);
                articleTag.setArticleId(updateAdminDTO.getId());
                return articleTag;
            }).collect(Collectors.toList());
            // 保存
            articleTagService.saveBatch(articleTagList);
        });
        // 更新文章信息
        Article article = BeanCopyUtils.copyBean(updateAdminDTO, Article.class);
        baseMapper.updateById(article);
    }

    /**
     * 添加文章信息(包括标签-文章信息)
     *
     * @param addAdminDTO 添加信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addArticle(ArticleAddAdminDTO addAdminDTO) {
        // 保存文章
        Article article = BeanCopyUtils.copyBean(addAdminDTO, Article.class);
        baseMapper.insert(article);
        // 插入标签信息
        Optional.ofNullable(addAdminDTO.getTagIdList()).ifPresent(tagIdList -> {
            List<ArticleTag> articleTagList = tagIdList.stream().map(tagId -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tagId);
                articleTag.setArticleId(article.getId());
                return articleTag;
            }).collect(Collectors.toList());
            // 保存
            articleTagService.saveBatch(articleTagList);
        });
    }

    /**
     * 统计发布文章有多少
     *
     * @return 发布文章的数量
     */
    @Override
    public Long countPublishedArticle() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        wrapper.eq(Article::getStatus, true);
        // 统计数量
        return baseMapper.selectCount(wrapper);
    }

    /**
     * 统计已经发布的文章的浏览数
     *
     * @return 已经发布的文章的浏览数
     */
    @Override
    public Long countPublishedArticleView() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 设置查询条件
        wrapper.select(Article::getViewCount);
        wrapper.eq(Article::getStatus, true);
        // 查询
        List<Article> articleList = baseMapper.selectList(wrapper);
        return articleList.stream().mapToLong(Article::getViewCount).sum();
    }

    /**
     * 获取文章归档
     *
     * @return 文章归档VO列表
     */
    @Override
    public List<ArticleArchiveUserVO> getArchiveList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Article::getId, Article::getTitle, Article::getCreateTime);
        List<Article> articleList = baseMapper.selectList(wrapper);
        // 按年份分组
        Map<Integer, List<Article>> collect = articleList.stream()
                .collect(Collectors.groupingBy(a -> a.getCreateTime().getYear()));
        List<ArticleArchiveUserVO> archiveList = new ArrayList<>();
        collect.forEach((k, v) -> {
            ArticleArchiveUserVO archive = new ArticleArchiveUserVO();
            archive.setYear(k + 1900);
            v.sort((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()));
            archive.setArticleList(v);
            archiveList.add(archive);
        });
        archiveList.sort((a, b) -> b.getYear() - a.getYear());
        return archiveList;
    }
}
