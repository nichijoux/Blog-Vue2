package com.zh.blog.mapper;

import com.zh.blog.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
