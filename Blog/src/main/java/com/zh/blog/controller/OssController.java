package com.zh.blog.controller;

import com.zh.blog.domain.Result;
import com.zh.blog.service.impl.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "上传api中心")
@RestController
@RequestMapping("blog/oss/")
public class OssController {
    private OssService ossService;

    @Autowired
    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    @ApiOperation(value = "上传图片文件")
    @PostMapping("uploadImage")
    public Result uploadImage(
            @ApiParam(name = "imageFile", value = "要上传的图片文件", required = true)
            @RequestParam("image") MultipartFile imageFile) {
        String url = ossService.uploadImage(imageFile);
        return Result.success(url);
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("uploadFile")
    public Result uploadFile(
            @ApiParam(name = "file", value = "要上传的文件", required = true)
            @RequestBody MultipartFile file) {
        String url = ossService.uploadFile(file);
        return Result.success(url);
    }
}
