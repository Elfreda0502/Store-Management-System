package com.game.store.category.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.store.category.domain.StoreCategory;

/**
 * 商品分类Service接口
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
public interface IStoreCategoryService extends IService<StoreCategory>
{
    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    public StoreCategory selectStoreCategoryById(Long id);

    /**
     * 查询商品分类列表
     * 
     * @param storeCategory 商品分类
     * @return 商品分类集合
     */
    public List<StoreCategory> selectStoreCategoryList(StoreCategory storeCategory);

    /**
     * 新增商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    public int insertStoreCategory(StoreCategory storeCategory);

    /**
     * 修改商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    public int updateStoreCategory(StoreCategory storeCategory);

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键集合
     * @return 结果
     */
    public int deleteStoreCategoryByIds(Long[] ids);

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    public int deleteStoreCategoryById(Long id);
}
