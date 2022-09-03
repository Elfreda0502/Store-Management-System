package com.game.system.service;

import com.game.common.core.domain.model.LoginUser;
import com.game.system.domain.SysUserOnline;

/**
 * 在线User 服务层
 * 
 * @author Yu Yue
 */
public interface ISysUserOnlineService
{
    /**
     * 通过登录地址查询信息
     * 
     * @param ipaddr 登录地址
     * @param user User信息
     * @return 在线User信息
     */
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    /**
     * 通过User名称查询信息
     * 
     * @param userName User名称
     * @param user User信息
     * @return 在线User信息
     */
    public SysUserOnline selectOnlineByUserName(String userName, LoginUser user);

    /**
     * 通过登录地址/User名称查询信息
     * 
     * @param ipaddr 登录地址
     * @param userName User名称
     * @param user User信息
     * @return 在线User信息
     */
    public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

    /**
     * 设置在线User信息
     * 
     * @param user User信息
     * @return 在线User
     */
    public SysUserOnline loginUserToUserOnline(LoginUser user);
}
