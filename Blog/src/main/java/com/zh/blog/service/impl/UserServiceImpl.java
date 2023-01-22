package com.zh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.blog.domain.dto.admin.UserAddAdminDTO;
import com.zh.blog.domain.dto.admin.UserAdminQueryConditionDTO;
import com.zh.blog.domain.dto.admin.UserUpdateAdminDTO;
import com.zh.blog.domain.dto.user.RegisterUserDTO;
import com.zh.blog.domain.dto.user.UserUpdateUserDTO;
import com.zh.blog.domain.entity.User;
import com.zh.blog.domain.vo.PageVO;
import com.zh.blog.domain.vo.admin.UserListAdminVO;
import com.zh.blog.domain.vo.user.UserInfoVO;
import com.zh.blog.enums.ResultErrorEnum;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.mapper.UserMapper;
import com.zh.blog.service.LinkService;
import com.zh.blog.service.UserRoleService;
import com.zh.blog.service.UserService;
import com.zh.blog.utils.BeanCopyUtils;
import com.zh.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author nichijoux
 * @since 2022-10-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserRoleService userRoleService;

    private LinkService linkService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息VO
     */
    @Override
    public UserInfoVO getUserInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = baseMapper.selectById(userId);
        return BeanCopyUtils.copyBean(user, UserInfoVO.class);
    }

    /**
     * 更新用户信息
     *
     * @param userInfoDTO 要更改的用户信息
     */
    @Override
    public void updateUserInfo(UserUpdateUserDTO userInfoDTO) {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id更新用户信息
        User user = BeanCopyUtils.copyBean(userInfoDTO, User.class);
        user.setId(userId);
        if (StringUtils.hasText(user.getPassword())) {
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
        }
        baseMapper.updateById(user);
    }

    /**
     * 普通用户注册
     *
     * @param registerDTO 用户注册输入的信息
     */
    @Override
    public void register(RegisterUserDTO registerDTO) {
        // 查询账号是否已经存在
        if (isFiledExist(User::getAccount, registerDTO.getAccount())) {
            throw new BlogException("用户名已经存在");
        }
        if (isFiledExist(User::getNickname, registerDTO.getNickname())) {
            throw new BlogException("用户昵称已经存在");
        }
        if (isFiledExist(User::getEmail, registerDTO.getEmail())) {
            throw new BlogException("该邮箱已经被注册");
        }
        // 对密码进行加密
        User user = BeanCopyUtils.copyBean(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // 存储
        if (baseMapper.insert(user) != 1) {
            throw new BlogException(ResultErrorEnum.REGISTER_FAILURE);
        }
    }

    /**
     * 管理员分页查询用户
     *
     * @param index             当前页
     * @param limit             每页记录数
     * @param queryConditionDTO 查询条件
     * @return 分页数据
     */
    @Override
    public PageVO pageQueryUser(Long index, Long limit, UserAdminQueryConditionDTO queryConditionDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 根据VO优化查询
        wrapper.select(User::getId, User::getNickname, User::getAccount,
                User::getEmail, User::getPhone, User::getAvatar, User::getType,
                User::getEnable);
        // 设置条件
        if (Objects.nonNull(queryConditionDTO)) {
            // 用户账号
            String account = queryConditionDTO.getAccount();
            wrapper.like(Objects.nonNull(account), User::getAccount, account);
            // 是否启用
            Boolean enable = queryConditionDTO.getEnable();
            wrapper.eq(Objects.nonNull(enable), User::getEnable, enable);
            // 用户类型
            Boolean type = queryConditionDTO.getType();
            wrapper.eq(Objects.nonNull(type), User::getType, type);
        }
        // 排序
        wrapper.orderByDesc(User::getType, User::getCreateTime);
        // 分页查询
        Page<User> userPage = new Page<>(index, limit);
        baseMapper.selectPage(userPage, wrapper);
        List<UserListAdminVO> userListAdminVOList = BeanCopyUtils.copyBeanList(userPage.getRecords(), UserListAdminVO.class);
        return BeanCopyUtils.copyPageVO(userPage, userListAdminVOList);
    }

    /**
     * 根据用户id删除用户(包括用户-角色关系)
     *
     * @param userId 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long userId) {
        // 删除用户-角色关系
        userRoleService.deleteUserRoleByUserId(userId);
        // 删除用户
        baseMapper.deleteById(userId);
    }

    /**
     * 根据用户id列表删除用户(包括用户-角色关系)
     *
     * @param userIdList 用户id列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDeleteUser(List<Long> userIdList) {
        // 删除用户-角色关系
        userRoleService.batchDeleteUserRoleByUserIdList(userIdList);
        // 删除用户
        baseMapper.deleteBatchIds(userIdList);
    }

    /**
     * 添加管理员用户
     *
     * @param addAdminDTO 管理员用户信息
     */
    @Override
    public void addAdminUser(UserAddAdminDTO addAdminDTO) {
        User user = BeanCopyUtils.copyBean(addAdminDTO, User.class);
        // 设置为管理员
        user.setType(true);
        // 查询是否符合条件
        if (isFiledExist(User::getAccount, addAdminDTO.getAccount())) {
            throw new BlogException("用户账号已经存在");
        }
        if (isFiledExist(User::getNickname, addAdminDTO.getNickname())) {
            throw new BlogException("用户昵称已经存在");
        }
        if (isFiledExist(User::getPhone, addAdminDTO.getPhone())) {
            throw new BlogException("手机号已经被绑定,请重试");
        }
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 添加管理员用户
        baseMapper.insert(user);
    }

    /**
     * 更新管理员用户信息
     *
     * @param updateAdminDTO 管理员用户的信息
     */
    @Override
    public void updateAdminUser(UserUpdateAdminDTO updateAdminDTO) {
        User updateUser = BeanCopyUtils.copyBean(updateAdminDTO, User.class);
        // 根据id获取本用户信息
        User nowUser = baseMapper.selectById(updateUser.getId());
        // 如果昵称更改了
        if (!Objects.equals(nowUser.getNickname(), updateUser.getNickname()) &&
                isFiledExist(User::getNickname, updateUser.getNickname())) {
            throw new BlogException("用户昵称已经存在");
        }
        // 如果电话号码更改了
        if (!Objects.equals(nowUser.getPhone(), updateUser.getPhone()) &&
                isFiledExist(User::getPhone, updateUser.getPhone())) {
            throw new BlogException("电话号码已经被使用");
        }
        // 如果邮箱更改了
        if (!Objects.equals(nowUser.getEmail(), updateUser.getEmail()) &&
                isFiledExist(User::getEmail, updateUser.getEmail())) {
            throw new BlogException("邮箱已经被使用");
        }
        // 更新管理员用户信息
        baseMapper.updateById(updateUser);
    }

    /**
     * 查询所有的管理员用户(仅包括id和nickname)
     *
     * @return 所有的管理员用户(仅包括id和nickname)
     */
    @Override
    public List<User> getAllAdminUser() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // select
        wrapper.select(User::getId, User::getNickname);
        // 设置查询条件
        wrapper.eq(User::getType, true);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据用户id获取头像
     *
     * @param idList 用户id列表
     * @return id-avatar Map
     */
    @Override
    public Map<Long, String> getIdAvatarMap(List<Long> idList) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getAvatar);
        wrapper.in(Objects.nonNull(idList), User::getId, idList);
        List<User> userList = baseMapper.selectList(wrapper);
        return userList.stream().collect(Collectors.toMap(User::getId, User::getAvatar));
    }

    /**
     * 判断字段值是否存在
     *
     * @param filed       字段
     * @param value       字段值
     * @param <FiledType> 字段
     * @return 是否存在
     */
    private <FiledType> boolean isFiledExist(SFunction<User, FiledType> filed, Object value) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(filed, value);
        return baseMapper.exists(wrapper);
    }
}
