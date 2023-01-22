package com.zh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.blog.domain.dto.admin.UserAddAdminDTO;
import com.zh.blog.domain.dto.admin.UserAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.UserUpdateAdminDTO;
import com.zh.blog.domain.dto.user.RegisterUserDTO;
import com.zh.blog.domain.dto.user.UserUpdateUserDTO;
import com.zh.blog.domain.entity.User;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.user.UserInfoVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
public interface UserService extends IService<User> {
    /**
     * 获取用户信息
     *
     * @return 用户信息VO
     */
    UserInfoVO getUserInfo();

    /**
     * 更新普通用户信息
     *
     * @param userInfoDTO 要更改的用户信息
     */
    void updateUserInfo(UserUpdateUserDTO userInfoDTO);

    /**
     * 普通用户注册
     *
     * @param registerDTO 用户注册输入的信息
     */
    void register(RegisterUserDTO registerDTO);

    /**
     * 管理员分页查询用户
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 分页数据
     */
    PageVO pageQueryUser(Long index, Long limit, UserAdminQueryConditionDTO queryConditionDTO);

    /**
     * 根据用户id删除用户(包括用户-角色关系)
     *
     * @param userId 用户id
     */
    void deleteUser(Long userId);

    /**
     * 根据用户id列表删除用户(包括用户-角色关系)
     *
     * @param userIdList 用户id列表
     */
    void batchDeleteUser(List<Long> userIdList);

    /**
     * 添加管理员用户
     *
     * @param addAdminDTO 管理员用户信息
     */
    void addAdminUser(UserAddAdminDTO addAdminDTO);

    /**
     * 更新管理员用户信息
     *
     * @param updateAdminDTO 管理员用户的信息
     */
    void updateAdminUser(UserUpdateAdminDTO updateAdminDTO);

    /**
     * 查询所有的管理员用户(仅包括id和nickname)
     *
     * @return 所有的管理员用户(仅包括id和nickname)
     */
    List<User> getAllAdminUser();

    /**
     * 根据用户id获取头像
     *
     * @param idList 用户id列表
     * @return id-avatar Map
     */
    Map<Long, String> getIdAvatarMap(List<Long> idList);
}
