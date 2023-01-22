package com.zh.blog.mapper;

import com.zh.blog.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoleMapper extends BaseMapper<Role> {
    // 根据用户id查询角色key
    List<String> selectKeyByUserId(Long userId);
}
