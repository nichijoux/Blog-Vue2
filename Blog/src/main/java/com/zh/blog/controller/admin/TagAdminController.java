package com.zh.blog.controller.admin;

import com.zh.blog.constants.RedisConstants;
import com.zh.blog.domain.Result;
import com.zh.blog.domain.dto.admin.TagAddAdminDTO;
import com.zh.blog.domain.dto.admin.TagAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.TagUpdateAdminDTO;
import com.zh.blog.domain.entity.Tag;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.TagEnableAdminVO;
import com.zh.blog.service.TagService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员标签api中心")
@RestController
@RequestMapping("/admin/tag")
public class TagAdminController {
    private TagService tagService;

    private RedisCache redisCache;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @ApiOperation(value = "管理员分页查询标签")
    @GetMapping("pageQueryTag/{index}/{limit}")
    public Result pageQueryTag(
            @ApiParam(name = "index", value = "页数", required = true)
            @PathVariable Long index,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "queryConditionDTO", value = "查询条件")
            @Validated TagAdminQueryConditionDTO queryConditionDTO) {
        PageVO pageVO = tagService.pageQueryTag(index, limit, queryConditionDTO);
        return Result.success(pageVO);
    }

    @ApiOperation(value = "获取所有启用的tag(仅包含id和name属性)")
    @GetMapping("getAllEnableTag")
    public Result getAllEnableTag() {
        List<Tag> tagList = tagService.getAllEnableTag();
        List<TagEnableAdminVO> tagEnableAdminVOList = BeanCopyUtils.copyBeanList(tagList, TagEnableAdminVO.class);
        return Result.success(tagEnableAdminVOList);
    }

    @ApiOperation(value = "添加标签")
    @PostMapping("addTag")
    public Result addTag(
            @ApiParam(name = "tagAddDTO", value = "标签添加DTO", required = true)
            @Validated @RequestBody TagAddAdminDTO tagAddDTO) {
        Tag tag = BeanCopyUtils.copyBean(tagAddDTO, Tag.class);
        tagService.save(tag);
        redisCache.deleteObject(RedisConstants.HOT_ARTICLE_LIST);
        return Result.success();
    }

    @ApiOperation(value = "更新标签")
    @PutMapping("updateTag")
    public Result updateTag(
            @ApiParam(name = "tagUpdateDTO", value = "标签更新DTO", required = true)
            @Validated @RequestBody TagUpdateAdminDTO tagUpdateDTO) {
        Tag tag = BeanCopyUtils.copyBean(tagUpdateDTO, Tag.class);
        tagService.updateById(tag);
        redisCache.deleteObject(RedisConstants.HOT_ARTICLE_LIST);
        return Result.success();
    }

    @ApiOperation(value = "启用或者禁用标签")
    @PutMapping("enableOrDisableTag/{tagId}")
    public Result enableOrDisableTag(
            @ApiParam(name = "tagId", value = "标签id", required = true)
            @PathVariable Long tagId,
            @ApiParam(name = "status", value = "标签状态", required = true)
                    Boolean status) {
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setEnable(status);
        tagService.updateById(tag);
        redisCache.deleteObject(RedisConstants.HOT_ARTICLE_LIST);
        return Result.success();
    }

    @ApiOperation(value = "根据标签id删除标签")
    @DeleteMapping("deleteTag/{tagId}")
    public Result deleteTag(
            @ApiParam(name = "tagId", value = "标签id", required = true)
            @PathVariable Long tagId) {
        // 直接删除tag并且删除tag-article关系
        tagService.deleteTag(tagId);
        redisCache.deleteObject(RedisConstants.HOT_ARTICLE_LIST);
        return Result.success();
    }
}
