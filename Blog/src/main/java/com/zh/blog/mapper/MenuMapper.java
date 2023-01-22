package com.zh.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.blog.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author nichijoux
 * @since 2022-11-14
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id获取权限值
     *
     * @param userId 用户id
     * @return 权限值列表
     */
    List<String> selectPermissionValueByUserId(Long userId);

    /**
     * 获取所有的路由菜单(仅包含目录和菜单)
     *
     * @return 路由菜单列表
     */
    List<Menu> selectAllRouterMenu();

    /**
     * 根据用户id获取其路由菜单(仅包含目录和菜单)
     *
     * @param userId 用户id
     * @return 路由菜单列表
     */
    List<Menu> selectRouterMenuByUserId(Long userId);
}
