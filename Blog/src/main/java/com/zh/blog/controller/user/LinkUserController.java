package com.zh.blog.controller.user;


import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.user.LinkAddUserDTO;
import com.zh.blog.domain.entity.Link;
import com.zh.blog.domain.vo.user.LinkUserVO;
import com.zh.blog.service.LinkService;
import com.zh.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Api(tags = "普通用户友链api中心")
@RestController
@RequestMapping("/user/link")
public class LinkUserController {

    private LinkService linkService;

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    @ApiOperation(value = "获取所有友链(包括审核通过、未通过、未审核的友链)")
    @GetMapping("getAllLink")
    private Result getAllLink() {
        List<LinkUserVO> linkUserVOList = linkService.getAllLink();
        return Result.success(linkUserVOList);
    }

    @ApiOperation(value = "添加友链")
    @PostMapping("addLink")
    private Result addLink(
            @ApiParam(name = "linkAddUserDTO", value = "添加友链的DTO", required = true)
            @Validated @RequestBody LinkAddUserDTO linkAddUserDTO) {
        Link link = BeanCopyUtils.copyBean(linkAddUserDTO, Link.class);
        linkService.save(link);
        return Result.success();
    }
}

