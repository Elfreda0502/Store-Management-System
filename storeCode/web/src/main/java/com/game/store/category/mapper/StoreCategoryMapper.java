package com.game.store.category.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.store.category.domain.StoreCategory;
import org.apache.ibatis.annotations.Mapper;


/**
 * 商品分类Mapper接口
 *
 * @author Yu Yue
 * @date 2022-05-13
 */
@Mapper
public interface StoreCategoryMapper extends BaseMapper<StoreCategory>
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
     * 删除商品分类
     *
     * @param id 商品分类主键
     * @return 结果
     */
    public int deleteStoreCategoryById(Long id);

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的Data主键集合
     * @return 结果
     */
    public int deleteStoreCategoryByIds(Long[] ids);
}
