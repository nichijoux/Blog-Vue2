package com.zh.blog.controller.admin;

import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.ArticleAddAdminDTO;
import com.zh.blog.domain.dto.admin.ArticleAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.ArticleUpdateAdminDTO;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.ArticleDetailAdminVO;
import com.zh.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 博客表 管理员前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-12
 */
@Api(tags = "管理员用户博客api中心")
@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {
    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "分页查询,获取文章(根据条件)")
    @GetMapping("pageQueryArticle/{index}/{limit}")
    public Result pageQueryArticle(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "queryConditionDTO", value = "查询条件")
            @Validated ArticleAdminQueryConditionDTO queryConditionDTO) {
        PageVO pageVO = articleService.pageQueryArticle(index, limit, queryConditionDTO);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "根据文章id获取文章详情信息")
    @GetMapping("getArticle/{articleId}")
    public Result getArticle(
            @ApiParam(name = "articleId", value = "文章id", required = true)
            @PathVariable Long articleId) {
        ArticleDetailAdminVO detail = articleService.getArticleDetail(articleId);
        return Result.success(detail);
    }

    @ApiOperation(value = "添加文章")
    @PostMapping("addArticle")
    public Result addArticle(
            @ApiParam(name = "addAdminDTO", value = "添加文章的DTO", required = true)
            @Validated @RequestBody ArticleAddAdminDTO addAdminDTO) {
        articleService.addArticle(addAdminDTO);
        return Result.success();
    }

    @ApiOperation(value = "更新文章")
    @PutMapping("updateArticle")
    public Result updateArticle(
            @ApiParam(name = "updateAdminDTO", value = "更新文章的DTO", required = true)
            @Validated @RequestBody ArticleUpdateAdminDTO updateAdminDTO) {
        articleService.updateArticle(updateAdminDTO);
        return Result.success();
    }

    @ApiOperation(value = "根据文章id删除文章,及文章-标签关系")
    @DeleteMapping("deleteArticle/{articleId}")
    public Result deleteArticle(
            @ApiParam(name = "articleId", value = "文章id", required = true)
            @PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return Result.success();
    }
}
