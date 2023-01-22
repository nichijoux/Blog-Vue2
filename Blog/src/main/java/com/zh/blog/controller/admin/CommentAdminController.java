package com.zh.blog.controller.admin;

import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.CommentAdminQueryConditionDTO;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "管理员端评论管理中心")
@RestController
@RequestMapping("admin/comment")
public class CommentAdminController {
    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "分页查询评论")
    @GetMapping("pageQueryComment/{index}/{limit}")
    public Result pageQueryComment(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "queryConditionDTO", value = "查询条件")
            @Validated CommentAdminQueryConditionDTO queryConditionDTO) {
        PageVO pageVO = commentService.pageQueryComment(index, limit, queryConditionDTO);
        return Result.success(pageVO);
    }
}
