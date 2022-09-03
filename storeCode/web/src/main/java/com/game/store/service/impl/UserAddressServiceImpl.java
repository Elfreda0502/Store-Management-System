package com.game.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.user.mapper.UserAddressMapper;
import com.game.user.domain.UserAddress;
import com.game.store.service.IUserAddressService;

/**
 * UseraddressService业务层处理
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
@Service
public class UserAddressServiceImpl implements IUserAddressService 
{
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 查询Useraddress
     * 
     * @param id Useraddress主键
     * @return Useraddress
     */
    @Override
    public UserAddress selectUserAddressById(Long id)
    {
        return userAddressMapper.selectUserAddressById(id);
    }

    /**
     * 查询Useraddress列表
     * 
     * @param userAddress Useraddress
     * @return Useraddress
     */
    @Override
    public List<UserAddress> selectUserAddressList(UserAddress userAddress)
    {
        return userAddressMapper.selectUserAddressList(userAddress);
    }

    /**
     * 新增Useraddress
     * 
     * @param userAddress Useraddress
     * @return 结果
     */
    @Override
    public int insertUserAddress(UserAddress userAddress)
    {
        return userAddressMapper.insertUserAddress(userAddress);
    }

    /**
     * 修改Useraddress
     * 
     * @param userAddress Useraddress
     * @return 结果
     */
    @Override
    public int updateUserAddress(UserAddress userAddress)
    {
        return userAddressMapper.updateUserAddress(userAddress);
    }

    /**
     * 批量删除Useraddress
     * 
     * @param ids 需要删除的Useraddress主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByIds(Long[] ids)
    {
        return userAddressMapper.deleteUserAddressByIds(ids);
    }

    /**
     * 删除Useraddress信息
     * 
     * @param id Useraddress主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressById(Long id)
    {
        return userAddressMapper.deleteUserAddressById(id);
    }
}
