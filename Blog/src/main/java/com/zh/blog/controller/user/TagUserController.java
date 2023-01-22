package com.zh.blog.controller.user;


import com.zh.blog.domain.Result;
import com.zh.blog.domain.entity.Tag;
import com.zh.blog.domain.vo.user.TagUserVO;
import com.zh.blog.service.TagService;
import com.zh.blog.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Api(tags = "普通用户标签api中心")
@RestController
@RequestMapping("/user/tag")
public class TagUserController {
    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiOperation(value = "获取所有启用的标签")
    @GetMapping("getAllEnableTag")
    public Result getAllEnableTag() {
        List<Tag> tagList = tagService.getAllEnableTag();
        List<TagUserVO> tagUserVOList = BeanCopyUtils.copyBeanList(tagList, TagUserVO.class);
        return Result.success(tagUserVOList);
    }
}

