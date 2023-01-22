package com.zh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.dto.admin.ArticleAddAdminDTO;
import com.zh.blog.domain.dto.admin.ArticleAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.ArticleUpdateAdminDTO;
import com.zh.blog.domain.entity.Article;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.ArticleDetailAdminVO;
import com.zh.blog.domain.vo.user.ArticleArchiveUserVO;
import com.zh.blog.domain.vo.user.ArticleDetailUserVO;
import com.zh.blog.domain.vo.user.HotArticleUserVO;

import java.util.List;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
public interface ArticleService extends IService<Article> {
    /**
     * 获取热门文章
     *
     * @return 热门文章列表
     */
    List<HotArticleUserVO> getHotArticle();

    /**
     * 用户端根据index,limit,tagId分页查询文章列表
     *
     * @param index 当前页
     * @param limit 每页记录数
     * @param tagId 标签id,可省略
     * @return 封装后的ListVO
     */
    PageVO pageQueryPublishedArticle(Long index, Long limit, Long tagId);

    /**
     * 根据文章id查询文章详情(2次mysql查询)
     *
     * @param articleId 文章id
     * @return 前台展示时的文章VO对象
     */
    ArticleDetailUserVO getPublishedArticleDetail(Long articleId);

    /**
     * 管理员根据index,limit,queryConditionDTO查询文章列表
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 封装后的PageVO
     */
    PageVO pageQueryArticle(Long index, Long limit, ArticleAdminQueryConditionDTO queryConditionDTO);

    /**
     * 根据文章id删除文章,及文章-标签关系
     *
     * @param articleId 要删除的文章id
     */
    void deleteArticle(Long articleId);

    /**
     * 根据文章id获取文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    ArticleDetailAdminVO getArticleDetail(Long articleId);

    /**
     * 更新文章(包括标签-文章信息)
     *
     * @param updateAdminDTO 更新信息
     */
    void updateArticle(ArticleUpdateAdminDTO updateAdminDTO);

    /**
     * 添加文章信息(包括标签-文章信息)
     *
     * @param addAdminDTO 添加信息
     */
    void addArticle(ArticleAddAdminDTO addAdminDTO);

    /**
     * 统计发布文章有多少
     *
     * @return 发布文章的数量
     */
    Long countPublishedArticle();

    /**
     * 统计已经发布的文章的浏览数
     *
     * @return 已经发布的文章的浏览数
     */
    Long countPublishedArticleView();

    /**
     * 获取文章归档
     *
     * @return 文章归档VO列表
     */
    List<ArticleArchiveUserVO> getArchiveList();
}
