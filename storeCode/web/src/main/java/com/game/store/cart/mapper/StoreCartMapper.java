package com.game.store.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.store.cart.domain.StoreCart;

import java.util.List;


/**
 * 购物车Mapper接口
 * 
 * @author Yu Yue
 * @date 2022-05-14
 */
public interface StoreCartMapper extends BaseMapper<StoreCart>
{
    /**
     * 查询购物车
     * 
     * @param id 购物车主键
     * @return 购物车
     */
    public StoreCart selectStoreCartById(Long id);

    /**
     * 查询购物车列表
     * 
     * @param storeCart 购物车
     * @return 购物车集合
     */
    public List<StoreCart> selectStoreCartList(StoreCart storeCart);

    /**
     * 新增购物车
     * 
     * @param storeCart 购物车
     * @return 结果
     */
    public int insertStoreCart(StoreCart storeCart);

    /**
     * 修改购物车
     * 
     * @param storeCart 购物车
     * @return 结果
     */
    public int updateStoreCart(StoreCart storeCart);

    /**
     * 删除购物车
     * 
     * @param id 购物车主键
     * @return 结果
     */
    public int deleteStoreCartById(Long id);

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的Data主键集合
     * @return 结果
     */
    public int deleteStoreCartByIds(Long[] ids);
}
