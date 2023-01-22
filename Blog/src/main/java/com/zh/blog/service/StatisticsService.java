package com.zh.blog.service;

import com.zh.blog.domain.vo.user.StatisticsUserVO;

public interface StatisticsService {
    /**
     * 获取用户端可见的统计数据(发布文章数,可用标签数,评论数,浏览数)
     *
     * @return 统计数据
     */
    StatisticsUserVO getBlogUserStatistics();
}
