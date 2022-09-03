package com.game.store.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.store.product.domain.StoreProduct;

import java.util.List;


/**
 * 商品列表Service接口
 * 
 * @author Yu Yue
 * @date 2022-05-14
 */
public interface IStoreProductService extends IService<StoreProduct>
{
    /**
     * 查询商品列表
     * 
     * @param id 商品列表主键
     * @return 商品列表
     */
    public StoreProduct selectStoreProductById(Long id);

    /**
     * 查询商品列表列表
     * 
     * @param storeProduct 商品列表
     * @return 商品列表集合
     */
    public List<StoreProduct> selectStoreProductList(StoreProduct storeProduct);

    /**
     * 新增商品列表
     * 
     * @param storeProduct 商品列表
     * @return 结果
     */
    public int insertStoreProduct(StoreProduct storeProduct);

    /**
     * 修改商品列表
     * 
     * @param storeProduct 商品列表
     * @return 结果
     */
    public int updateStoreProduct(StoreProduct storeProduct);

    /**
     * 批量删除商品列表
     * 
     * @param ids 需要删除的商品列表主键集合
     * @return 结果
     */
    public int deleteStoreProductByIds(Long[] ids);

    /**
     * 删除商品列表信息
     * 
     * @param id 商品列表主键
     * @return 结果
     */
    public int deleteStoreProductById(Long id);
}
