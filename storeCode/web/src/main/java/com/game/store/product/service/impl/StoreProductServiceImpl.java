package com.game.store.product.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.store.product.domain.StoreProduct;
import com.game.store.product.mapper.StoreProductMapper;
import com.game.store.product.service.IStoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 商品列表Service业务层处理
 * 
 * @author Yu Yue
 * @date 2022-05-14
 */
@Service
public class StoreProductServiceImpl extends ServiceImpl<StoreProductMapper,StoreProduct> implements IStoreProductService
{
    @Autowired
    private StoreProductMapper storeProductMapper;

    /**
     * 查询商品列表
     * 
     * @param id 商品列表主键
     * @return 商品列表
     */
    @Override
    public StoreProduct selectStoreProductById(Long id)
    {
        return storeProductMapper.selectStoreProductById(id);
    }

    /**
     * 查询商品列表列表
     * 
     * @param storeProduct 商品列表
     * @return 商品列表
     */
    @Override
    public List<StoreProduct> selectStoreProductList(StoreProduct storeProduct)
    {
        return storeProductMapper.selectStoreProductList(storeProduct);
    }

    /**
     * 新增商品列表
     * 
     * @param storeProduct 商品列表
     * @return 结果
     */
    @Override
    public int insertStoreProduct(StoreProduct storeProduct)
    {
        return storeProductMapper.insertStoreProduct(storeProduct);
    }

    /**
     * 修改商品列表
     * 
     * @param storeProduct 商品列表
     * @return 结果
     */
    @Override
    public int updateStoreProduct(StoreProduct storeProduct)
    {
        return storeProductMapper.updateStoreProduct(storeProduct);
    }

    /**
     * 批量删除商品列表
     * 
     * @param ids 需要删除的商品列表主键
     * @return 结果
     */
    @Override
    public int deleteStoreProductByIds(Long[] ids)
    {
        return storeProductMapper.deleteStoreProductByIds(ids);
    }

    /**
     * 删除商品列表信息
     * 
     * @param id 商品列表主键
     * @return 结果
     */
    @Override
    public int deleteStoreProductById(Long id)
    {
        return storeProductMapper.deleteStoreProductById(id);
    }
}
