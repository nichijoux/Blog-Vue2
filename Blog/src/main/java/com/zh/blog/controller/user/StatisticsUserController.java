package com.zh.blog.controller.user;

import com.zh.blog.domain.Result;
import com.zh.blog.domain.vo.user.StatisticsUserVO;
import com.zh.blog.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "普通用户统计数据api中心")
@RestController
@RequestMapping("/user/statistics")
public class StatisticsUserController {
    private StatisticsService statisticsService;

    @Autowired
    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @ApiOperation(value = "用户端获取统计数据,包括(发布文章数,可用标签数,评论数,浏览数)")
    @GetMapping("getBlogUserStatistics")
    private Result getBlogUserStatistics() {
        StatisticsUserVO userVO = statisticsService.getBlogUserStatistics();
        return Result.success(userVO);
    }
}
