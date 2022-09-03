package com.game.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import com.game.common.core.domain.entity.SysUser;

/**
 * User表 Data层
 * 
 * @author Yu Yue
 */
public interface SysUserMapper extends BaseMapper<SysUser>
{
    /**
     * 根据条件分页查询User列表
     * 
     * @param sysUser User信息
     * @return User信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询已配User角色列表
     * 
     * @param user User信息
     * @return User信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配User角色列表
     * 
     * @param user User信息
     * @return User信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过User名查询User
     * 
     * @param userName User名
     * @return User对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过UserID查询User
     * 
     * @param userId UserID
     * @return User对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增User信息
     * 
     * @param user User信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改User信息
     * 
     * @param user User信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改User头像
     * 
     * @param userName User名
     * @param avatar 头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置User密码
     * 
     * @param userName User名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过UserID删除User
     * 
     * @param userId UserID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除User信息
     * 
     * @param userIds 需要删除的UserID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 校验User名称是否唯一
     * 
     * @param userName User名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email User邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);
}
