package com.game.store.order.service;

import com.game.store.order.domain.StoreOrder;

import java.util.List;

/**
 * 订单Service接口
 * 
 * @author Yu Yue
 * @date 2022-05-22
 */
public interface IStoreOrderService 
{
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    public StoreOrder selectStoreOrderById(String id);

    /**
     * 查询订单列表
     * 
     * @param storeOrder 订单
     * @return 订单集合
     */
    public List<StoreOrder> selectStoreOrderList(StoreOrder storeOrder);

    /**
     * 新增订单
     * 
     * @param storeOrder 订单
     * @return 结果
     */
    public int insertStoreOrder(StoreOrder storeOrder);

    /**
     * 修改订单
     * 
     * @param storeOrder 订单
     * @return 结果
     */
    public int updateStoreOrder(StoreOrder storeOrder);

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteStoreOrderByIds(String[] ids);

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    public int deleteStoreOrderById(String id);
}
