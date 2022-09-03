package com.game.store.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.store.product.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 商品列表Mapper接口
 *
 * @author Yu Yue
 * @date 2022-05-14
 */
@Mapper
public interface StoreProductMapper extends BaseMapper<StoreProduct>
{
    /**
     * 查询商品列表
     *
     * @param id 商品列表主键
     * @return 商品列表
     */
    public StoreProduct selectStoreProductById(Long id);


    /**
     * get CateCountData
     * @return
     */
    public List<StoreCategoryCount> selectCateCountData();

    /**
     * get CateCountData
     * @return
     */
    public List<StoreSales> selectStoreSales();

    /**
     *  GET GOOD
     * @return
     */
    public GoodProduct selectGoodStore();


    public List<StoreProduct> selectPoorStore();


    /**
     * 获取菜单列表
     * @return
     */
    public List<String> getCategoryList();


    /**
     * 获取最近七天
     * @return
     */
    public List<String> getDays();

    public StoreCategorySales getStoreCategorySales(@Param("date") String date, @Param("name") String name);
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
     * 删除商品列表
     *
     * @param id 商品列表主键
     * @return 结果
     */
    public int deleteStoreProductById(Long id);

    /**
     * 批量删除商品列表
     *
     * @param ids 需要删除的Data主键集合
     * @return 结果
     */
    public int deleteStoreProductByIds(Long[] ids);
}
