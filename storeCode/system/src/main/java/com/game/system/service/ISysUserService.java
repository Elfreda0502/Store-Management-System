package com.game.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.common.core.domain.entity.SysUser;

/**
 * User 业务层
 * 
 * @author Yu Yue
 */
public interface ISysUserService extends IService<SysUser>
{
    /**
     * 根据条件分页查询User列表
     * 
     * @param user User信息
     * @return User信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根据条件分页查询已分配User角色列表
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
     * 根据UserID查询User所属角色组
     * 
     * @param userName User名
     * @return 结果
     */
    public String selectUserRoleGroup(String userName);

    /**
     * 根据UserID查询User所属岗位组
     * 
     * @param userName User名
     * @return 结果
     */
    public String selectUserPostGroup(String userName);

    /**
     * 校验User名称是否唯一
     * 
     * @param userName User名称
     * @return 结果
     */
    public String checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user User信息
     * @return 结果
     */
    public String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user User信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);

    /**
     * 校验User是否允许操作
     * 
     * @param user User信息
     */
    public void checkUserAllowed(SysUser user);

    /**
     * 校验User是否有Data权限
     * 
     * @param userId Userid
     */
    public void checkUserDataScope(Long userId);

    /**
     * 新增User信息
     * 
     * @param user User信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 注册User信息
     * 
     * @param user User信息
     * @return 结果
     */
    public boolean registerUser(SysUser user);

    /**
     * 修改User信息
     * 
     * @param user User信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * User授权角色
     * 
     * @param userId UserID
     * @param roleIds 角色组
     */
    public void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * 修改User State
     * 
     * @param user User信息
     * @return 结果
     */
    public int updateUserStatus(SysUser user);

    /**
     * 修改User基本信息
     * 
     * @param user User信息
     * @return 结果
     */
    public int updateUserProfile(SysUser user);

    /**
     * 修改User头像
     * 
     * @param userName User名
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(String userName, String avatar);

    /**
     * 重置User密码
     * 
     * @param user User信息
     * @return 结果
     */
    public int resetPwd(SysUser user);

    /**
     * 重置User密码
     * 
     * @param userName User名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(String userName, String password);

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
     * ImportUserData
     * 
     * @param userList UserData列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新Data
     * @param operName 操作User
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
}
