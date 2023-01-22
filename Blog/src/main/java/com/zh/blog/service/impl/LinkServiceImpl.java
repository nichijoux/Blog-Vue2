package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.domain.dto.admin.LinkAdminQueryConditionDTO;
import com.zh.blog.domain.entity.Link;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.LinkListAdminVO;
import com.zh.blog.domain.vo.user.LinkUserVO;
import com.zh.blog.mapper.LinkMapper;
import com.zh.blog.service.LinkService;
import com.zh.blog.service.UserService;
import com.zh.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 管理员分页查询友链
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return PageVO
     */
    @Override
    public PageVO pageQueryLink(Long index, Long limit, LinkAdminQueryConditionDTO queryConditionDTO) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(Link::getId, Link::getName, Link::getLogo,
                Link::getAddress, Link::getDescription, Link::getStatus);
        // 设置查询条件
        if (Objects.nonNull(queryConditionDTO)) {
            // 友链名称
            String name = queryConditionDTO.getName();
            wrapper.like(Objects.nonNull(name), Link::getName, name);
            // 友链描述
            String description = queryConditionDTO.getDescription();
            wrapper.like(Objects.nonNull(description), Link::getDescription, description);
            // 友链状态
            Integer status = queryConditionDTO.getStatus();
            wrapper.eq(Objects.nonNull(status), Link::getStatus, status);
        }
        // 分页查询
        Page<Link> linkPage = new Page<>(index, limit);
        baseMapper.selectPage(linkPage, wrapper);
        List<LinkListAdminVO> linkListAdminVOList = BeanCopyUtils.copyBeanList(linkPage.getRecords(), LinkListAdminVO.class);
        return BeanCopyUtils.copyPageVO(linkPage, linkListAdminVOList);
    }

    /**
     * 获取所有的友链(包括审核通过、未通过、未审核的友链)
     *
     * @return 友链列表
     */
    @Override
    public List<LinkUserVO> getAllLink() {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        // 优化查询
        wrapper.select(Link::getId, Link::getName, Link::getLogo, Link::getAddress
                , Link::getStatus, Link::getCreateBy);
        // 查询
        List<Link> linkList = baseMapper.selectList(wrapper);
        Map<Long, Long> collect = linkList.stream().collect(Collectors.toMap(Link::getId, Link::getCreateBy));
        // 查询用户头像
        Map<Long, String> idAvatarMap = userService.getIdAvatarMap(
                linkList.stream().map(Link::getCreateBy).collect(Collectors.toList()));
        // 设置到linkUserVO中
        List<LinkUserVO> linkUserVOList = BeanCopyUtils.copyBeanList(linkList, LinkUserVO.class);
        linkUserVOList.forEach(link -> link.setAvatar(idAvatarMap.get(collect.get(link.getId()))));
        return linkUserVOList;
    }
}
