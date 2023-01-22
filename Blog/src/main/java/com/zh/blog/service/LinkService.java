package com.zh.blog.service;

import com.zh.blog.domain.dto.admin.LinkAdminQueryConditionDTO;
import com.zh.blog.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.user.LinkUserVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
public interface LinkService extends IService<Link> {
    /**
     * 管理员分页查询友链
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return PageVO
     */
    PageVO pageQueryLink(Long index, Long limit, LinkAdminQueryConditionDTO queryConditionDTO);

    /**
     * 获取所有的友链(包括审核通过、未通过、未审核的友链)
     * @return 友链列表
     */
    List<LinkUserVO> getAllLink();
}
