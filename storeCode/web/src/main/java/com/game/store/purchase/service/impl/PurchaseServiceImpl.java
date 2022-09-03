package com.game.store.purchase.service.impl;

import java.util.List;

import com.game.store.purchase.domain.Purchase;
import com.game.store.purchase.mapper.PurchaseMapper;
import com.game.store.purchase.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 采购Service业务层处理
 * 
 * @author store
 * @date 2022-05-31
 */
@Service
public class PurchaseServiceImpl implements IPurchaseService
{
    @Autowired
    private PurchaseMapper purchaseMapper;

    /**
     * 查询采购
     * 
     * @param id 采购主键
     * @return 采购
     */
    @Override
    public Purchase selectPurchaseById(Long id)
    {
        return purchaseMapper.selectPurchaseById(id);
    }

    /**
     * 查询采购列表
     * 
     * @param purchase 采购
     * @return 采购
     */
    @Override
    public List<Purchase> selectPurchaseList(Purchase purchase)
    {
        return purchaseMapper.selectPurchaseList(purchase);
    }

    /**
     * 新增采购
     * 
     * @param purchase 采购
     * @return 结果
     */
    @Override
    public int insertPurchase(Purchase purchase)
    {
        return purchaseMapper.insertPurchase(purchase);
    }

    /**
     * 修改采购
     * 
     * @param purchase 采购
     * @return 结果
     */
    @Override
    public int updatePurchase(Purchase purchase)
    {
        return purchaseMapper.updatePurchase(purchase);
    }

    /**
     * 批量删除采购
     * 
     * @param ids 需要删除的采购主键
     * @return 结果
     */
    @Override
    public int deletePurchaseByIds(Long[] ids)
    {
        return purchaseMapper.deletePurchaseByIds(ids);
    }

    /**
     * 删除采购信息
     * 
     * @param id 采购主键
     * @return 结果
     */
    @Override
    public int deletePurchaseById(Long id)
    {
        return purchaseMapper.deletePurchaseById(id);
    }
}
