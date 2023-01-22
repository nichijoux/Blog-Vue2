package com.zh.blog.controller.admin;

import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.LinkAddAdminDTO;
import com.zh.blog.domain.dto.admin.LinkAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.LinkUpdateAdminDTO;
import com.zh.blog.domain.entity.Link;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.service.LinkService;
import com.zh.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "管理员友链api中心")
@RestController
@RequestMapping("/admin/link")
public class LinkAdminController {
    private LinkService linkService;

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    @ApiOperation(value = "分页查询友链")
    @GetMapping("pageQueryLink/{index}/{limit}")
    public Result pageQueryLink(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "queryConditionDTO", value = "查询条件")
            @Validated LinkAdminQueryConditionDTO queryConditionDTO) {
        PageVO pageVO = linkService.pageQueryLink(index, limit, queryConditionDTO);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "添加友链信息")
    @PutMapping("addLink")
    public Result addLink(
            @ApiParam(name = "updateAdminDTO", value = "添加友链的DTO", required = true)
            @Validated @RequestBody LinkAddAdminDTO addAdminDTO) {
        Link link = BeanCopyUtils.copyBean(addAdminDTO, Link.class);
        linkService.save(link);
        return Result.success();
    }

    @ApiOperation(value = "更新友链信息")
    @PutMapping("updateLink")
    public Result updateLink(
            @ApiParam(name = "updateAdminDTO", value = "更新友链的DTO", required = true)
            @Validated @RequestBody LinkUpdateAdminDTO updateAdminDTO) {
        Link link = BeanCopyUtils.copyBean(updateAdminDTO, Link.class);
        linkService.updateById(link);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除友链")
    @DeleteMapping("deleteLink/{id}")
    public Result deleteLink(
            @ApiParam(name = "id", value = "友链id", required = true)
            @PathVariable Long id) {
        linkService.removeById(id);
        return Result.success();
    }
}
