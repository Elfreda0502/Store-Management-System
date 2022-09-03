package com.game.user.mapper;

import java.util.List;
import com.game.user.domain.UserAddress;

/**
 * UseraddressMapper接口
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
public interface UserAddressMapper 
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
     * 删除Useraddress
     * 
     * @param id Useraddress主键
     * @return 结果
     */
    public int deleteUserAddressById(Long id);

    /**
     * 批量删除Useraddress
     * 
     * @param ids 需要删除的Data主键集合
     * @return 结果
     */
    public int deleteUserAddressByIds(Long[] ids);
}
