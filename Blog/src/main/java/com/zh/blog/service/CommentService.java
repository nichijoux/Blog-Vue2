package com.zh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.dto.admin.CommentAdminQueryConditionDTO;
import com.zh.blog.domain.dto.user.CommentAddUserDTO;
import com.zh.blog.domain.entity.Comment;
import com.zh.blog.domain.vo.PageVO;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
public interface CommentService extends IService<Comment> {

    /**
     * 用户端分页查询文章的评论
     *
     * @param index     当前页
     * @param limit     每页记录数
     * @param articleId 文章id
     * @return 封装完成后前台的显示内容
     */
    PageVO pageQueryArticleComment(Long index, Long limit, Long articleId);

    /**
     * 用户端添加评论
     *
     * @param addCommentUserDTO 添加的表单数据
     */
    void addComment(CommentAddUserDTO addCommentUserDTO);

    /**
     * 管理员端分页查询评论
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 评论分页情况
     */
    PageVO pageQueryComment(Long index, Long limit, CommentAdminQueryConditionDTO queryConditionDTO);

    /**
     * 获取发布文章的评论数量
     *
     * @return 发布文章的评论数量
     */
    Long countPublishedArticleComment();
}
