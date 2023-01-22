package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.constants.SystemConstants;
import com.zh.blog.domain.dto.admin.CommentAdminQueryConditionDTO;
import com.zh.blog.domain.dto.user.CommentAddUserDTO;
import com.zh.blog.domain.entity.Article;
import com.zh.blog.domain.entity.Comment;
import com.zh.blog.domain.entity.User;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.CommentListAdminVO;
import com.zh.blog.domain.vo.user.CommentUserVO;
import com.zh.blog.mapper.CommentMapper;
import com.zh.blog.service.ArticleService;
import com.zh.blog.service.CommentService;
import com.zh.blog.service.UserService;
import com.zh.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private UserService userService;

    private ArticleService articleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 用户端分页查询文章的评论
     *
     * @param index     当前页
     * @param limit     每页记录数
     * @param articleId 文章id
     * @return 封装完成后前台的显示内容
     */
    @Override
    public PageVO pageQueryArticleComment(Long index, Long limit, Long articleId) {
        // 查询根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Comment::getId, Comment::getRootId, Comment::getContent,
                Comment::getArticleId, Comment::getToCommentUserId, Comment::getToCommentId,
                Comment::getCreateBy, Comment::getCreateTime);
        // 设置查询条件
        wrapper.eq(Comment::getArticleId, articleId);
        // 根评论的rootId设置
        wrapper.eq(Comment::getRootId, SystemConstants.COMMENT_IS_ROOT_ID);
        // 设置排序类型
        wrapper.orderByDesc(Comment::getCreateTime);
        // 分页查询根评论
        Page<Comment> commentPage = new Page<>(index, limit);
        baseMapper.selectPage(commentPage, wrapper);
        Map<Long, User> userMap = new HashMap<>();
        // VO列表
        List<CommentUserVO> commentUserVOList = convertToCommentUserVOList(commentPage.getRecords(), userMap);
        // 查询所有子评论集合,并赋值给对应属性
        commentUserVOList.forEach(commentUserVO -> {
            List<CommentUserVO> commentChildren = getCommentChildren(commentUserVO.getId(), userMap);
            commentUserVO.setChildren(commentChildren);
        });
        return BeanCopyUtils.copyPageVO(commentPage, commentUserVOList);
    }

    /**
     * 用户端添加评论
     *
     * @param addCommentUserDTO 添加的表单数据
     */
    @Override
    public void addComment(CommentAddUserDTO addCommentUserDTO) {
        if (addCommentUserDTO.getRootId().equals(SystemConstants.COMMENT_IS_ROOT_ID)) {
            addCommentUserDTO.setToCommentId(SystemConstants.COMMENT_IS_ROOT_ID);
            addCommentUserDTO.setToCommentUserId(SystemConstants.COMMENT_IS_ROOT_ID);
        }
        Comment comment = BeanCopyUtils.copyBean(addCommentUserDTO, Comment.class);
        baseMapper.insert(comment);
    }

    /**
     * 管理员端分页查询评论
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 评论分页情况
     */
    @Override
    public PageVO pageQueryComment(Long index, Long limit, CommentAdminQueryConditionDTO queryConditionDTO) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Comment::getId, Comment::getContent, Comment::getArticleId,
                Comment::getToCommentId, Comment::getCreateBy, Comment::getCreateTime);
        // 设置排序
        wrapper.orderByDesc(Comment::getCreateTime);
        // 分页查询
        Page<Comment> commentPage = new Page<>(index, limit);
        baseMapper.selectPage(commentPage, wrapper);
        List<CommentListAdminVO> commentListAdminVOList = BeanCopyUtils.copyBeanList(commentPage.getRecords(), CommentListAdminVO.class);
        // 查询文章名
        commentListAdminVOList.forEach(comment -> {
            Article article = articleService.getById(comment.getArticleId());
            Optional.ofNullable(article).ifPresent(a ->
                    comment.setArticleTitle(a.getTitle()));
        });
        // 查询用户账号
        commentListAdminVOList.forEach(comment -> {
            User user = userService.getById(comment.getCreateBy());
            Optional.ofNullable(user).ifPresent(u ->
                    comment.setAccount(u.getAccount()));
        });
        return BeanCopyUtils.copyPageVO(commentPage, commentListAdminVOList);
    }

    /**
     * 获取发布文章的评论数量
     *
     * @return 发布文章的评论数量
     */
    @Override
    public Long countPublishedArticleComment() {
        return baseMapper.countPublishedArticleComment();
    }

    /**
     * 根据根评论id获取其子评论
     *
     * @param rootId  根评论id
     * @param userMap userMap缓存
     * @return 子评论VO
     */
    private List<CommentUserVO> getCommentChildren(Long rootId, Map<Long, User> userMap) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Comment::getId, Comment::getRootId, Comment::getContent,
                Comment::getArticleId, Comment::getToCommentUserId, Comment::getToCommentId,
                Comment::getCreateBy, Comment::getCreateTime);
        // 设置查询条件
        wrapper.eq(Comment::getRootId, rootId);
        // 设置排序条件
        wrapper.orderByAsc(Comment::getCreateTime);
        // 进行查询
        List<Comment> commentList = baseMapper.selectList(wrapper);
        return convertToCommentUserVOList(commentList, userMap);
    }

    /**
     * 将Comment列表转化为CommentUserVO列表
     *
     * @param commentList 评论列表
     * @param userMap     userMap缓存
     * @return CommentUserVO列表
     */
    private List<CommentUserVO> convertToCommentUserVOList(List<Comment> commentList, Map<Long, User> userMap) {
        List<CommentUserVO> commentUserVOList = BeanCopyUtils.copyBeanList(commentList, CommentUserVO.class);
        // 遍历VO集合
        commentUserVOList.forEach(commentUserVO -> {
            // 本评论的昵称
            User user = userMap.get(commentUserVO.getCreateBy());
            if (Objects.isNull(user)) {
                user = userService.getById(commentUserVO.getCreateBy());
                userMap.put(commentUserVO.getCreateBy(), user);
            }
            commentUserVO.setNickname(user.getNickname());
            commentUserVO.setAvatar(user.getAvatar());
        });
        return commentUserVOList;
    }
}
