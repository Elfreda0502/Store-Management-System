package com.game.store.cart.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.common.utils.DateUtils;
import com.game.store.cart.domain.StoreCart;
import com.game.store.cart.mapper.StoreCartMapper;
import com.game.store.cart.service.IStoreCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 购物车Service业务层处理
 * 
 * @author Yu Yue
 * @date 2022-05-14
 */
@Service
public class StoreCartServiceImpl extends ServiceImpl<StoreCartMapper,StoreCart> implements IStoreCartService
{
    @Autowired
    private StoreCartMapper storeCartMapper;

    /**
     * 查询购物车
     * 
     * @param id 购物车主键
     * @return 购物车
     */
    @Override
    public StoreCart selectStoreCartById(Long id)
    {
        return storeCartMapper.selectStoreCartById(id);
    }

    /**
     * 查询购物车列表
     * 
     * @param storeCart 购物车
     * @return 购物车
     */
    @Override
    public List<StoreCart> selectStoreCartList(StoreCart storeCart)
    {
        return storeCartMapper.selectStoreCartList(storeCart);
    }

    /**
     * 新增购物车
     * 
     * @param storeCart 购物车
     * @return 结果
     */
    @Override
    public int insertStoreCart(StoreCart storeCart)
    {
        storeCart.setCreateTime(DateUtils.getNowDate());
        return storeCartMapper.insertStoreCart(storeCart);
    }

    /**
     * 修改购物车
     * 
     * @param storeCart 购物车
     * @return 结果
     */
    @Override
    public int updateStoreCart(StoreCart storeCart)
    {
        storeCart.setUpdateTime(DateUtils.getNowDate());
        return storeCartMapper.updateStoreCart(storeCart);
    }

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteStoreCartByIds(Long[] ids)
    {
        return storeCartMapper.deleteStoreCartByIds(ids);
    }

    /**
     * 删除购物车信息
     * 
     * @param id 购物车主键
     * @return 结果
     */
    @Override
    public int deleteStoreCartById(Long id)
    {
        return storeCartMapper.deleteStoreCartById(id);
    }
}
