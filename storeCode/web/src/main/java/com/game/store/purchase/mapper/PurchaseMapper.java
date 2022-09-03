package com.game.store.purchase.mapper;

import com.game.store.purchase.domain.Purchase;

import java.util.List;


/**
 * 采购Mapper接口
 * 
 * @author store
 * @date 2022-05-31
 */
public interface PurchaseMapper 
{
    /**
     * 查询采购
     * 
     * @param id 采购主键
     * @return 采购
     */
    public Purchase selectPurchaseById(Long id);

    /**
     * 查询采购列表
     * 
     * @param purchase 采购
     * @return 采购集合
     */
    public List<Purchase> selectPurchaseList(Purchase purchase);

    /**
     * 新增采购
     * 
     * @param purchase 采购
     * @return 结果
     */
    public int insertPurchase(Purchase purchase);

    /**
     * 修改采购
     * 
     * @param purchase 采购
     * @return 结果
     */
    public int updatePurchase(Purchase purchase);

    /**
     * 删除采购
     * 
     * @param id 采购主键
     * @return 结果
     */
    public int deletePurchaseById(Long id);

    /**
     * 批量删除采购
     * 
     * @param ids 需要删除的Data主键集合
     * @return 结果
     */
    public int deletePurchaseByIds(Long[] ids);
}
