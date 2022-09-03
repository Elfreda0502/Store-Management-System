package com.game.store.category.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.store.category.domain.StoreCategory;
import com.game.store.category.mapper.StoreCategoryMapper;
import com.game.store.category.service.IStoreCategoryService;
import com.game.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 商品分类Service业务层处理
 * 
 * @author Yu Yue
 * @date 2022-05-13
 */
@Service
public class StoreCategoryServiceImpl extends ServiceImpl<StoreCategoryMapper, StoreCategory> implements IStoreCategoryService
{
    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    @Override
    public StoreCategory selectStoreCategoryById(Long id)
    {
        return storeCategoryMapper.selectStoreCategoryById(id);
    }

    /**
     * 查询商品分类列表
     * 
     * @param storeCategory 商品分类
     * @return 商品分类
     */
    @Override
    public List<StoreCategory> selectStoreCategoryList(StoreCategory storeCategory)
    {
        return storeCategoryMapper.selectStoreCategoryList(storeCategory);
    }

    /**
     * 新增商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    @Override
    public int insertStoreCategory(StoreCategory storeCategory)
    {
        storeCategory.setCreateTime(DateUtils.getNowDate());
        return storeCategoryMapper.insertStoreCategory(storeCategory);
    }

    /**
     * 修改商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    @Override
    public int updateStoreCategory(StoreCategory storeCategory)
    {
        storeCategory.setUpdateTime(DateUtils.getNowDate());
        return storeCategoryMapper.updateStoreCategory(storeCategory);
    }

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteStoreCategoryByIds(Long[] ids)
    {
        return storeCategoryMapper.deleteStoreCategoryByIds(ids);
    }

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteStoreCategoryById(Long id)
    {
        return storeCategoryMapper.deleteStoreCategoryById(id);
    }
}
