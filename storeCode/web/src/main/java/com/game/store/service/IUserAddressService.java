package com.game.store.service;

import java.util.List;
import com.game.user.domain.UserAddress;

/**
 * UseraddressService接口
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
public interface IUserAddressService 
{
    /**
     * 查询Useraddress
     * 
     * @param id Useraddress主键
     * @return Useraddress
     */
    public UserAddress selectUserAddressById(Long id);

    /**
     * 查询Useraddress列表
     * 
     * @param userAddress Useraddress
     * @return Useraddress集合
     */
    public List<UserAddress> selectUserAddressList(UserAddress userAddress);

    /**
     * 新增Useraddress
     * 
     * @param userAddress Useraddress
     * @return 结果
     */
    public int insertUserAddress(UserAddress userAddress);

    /**
     * 修改Useraddress
     * 
     * @param userAddress Useraddress
     * @return 结果
     */
    public int updateUserAddress(UserAddress userAddress);

    /**
     * 批量删除Useraddress
     * 
     * @param ids 需要删除的Useraddress主键集合
     * @return 结果
     */
    public int deleteUserAddressByIds(Long[] ids);

    /**
     * 删除Useraddress信息
     * 
     * @param id Useraddress主键
     * @return 结果
     */
    public int deleteUserAddressById(Long id);
}
