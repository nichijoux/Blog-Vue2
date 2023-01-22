package com.zh.blog.controller.user;


import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.user.CommentAddUserDTO;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Api(tags = "普通用户评论api中心")
@RestController
@RequestMapping("/user/comment")
public class CommentUserController {
    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "分页查询某博客的评论")
    @GetMapping("pageQueryArticleComment/{index}/{limit}")
    public Result pageQueryArticleComment(
            @ApiParam(name = "index", value = "当前页", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "articleId", value = "文章id", required = true)
            @RequestParam("articleId") Long articleId) {
        PageVO pageVO = commentService.pageQueryArticleComment(index, limit, articleId);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "用户评论")
    @PostMapping("addComment")
    public Result addComment(
            @ApiParam(name = "addCommentUserDTO", value = "添加评论的表单", required = true)
            @Validated @RequestBody CommentAddUserDTO addCommentUserDTO) {
        commentService.addComment(addCommentUserDTO);
        return Result.success();
    }
}