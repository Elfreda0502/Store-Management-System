package com.game.store.product.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.game.common.utils.poi.ExcelUtil;
import com.game.store.category.domain.StoreCategory;
import com.game.store.category.service.IStoreCategoryService;
import com.game.store.product.domain.*;
import com.game.store.product.mapper.StoreProductMapper;
import com.game.store.product.service.IStoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.game.common.annotation.Log;
import com.game.common.core.controller.BaseController;
import com.game.common.core.domain.AjaxResult;
import com.game.common.enums.BusinessType;

import com.game.common.core.page.TableDataInfo;

/**
 * Product List Controller
 *
 * @author Yu Yue
 * @date 2022-05-14
 */
@RestController
@RequestMapping("/store/product")
public class StoreProductController extends BaseController
{
    @Autowired
    private IStoreProductService storeProductService;

    @Autowired
    private IStoreCategoryService storeCategoryService;

    @Autowired
    private StoreProductMapper storeProductMapper;


    @GetMapping("/index")
    public AjaxResult index(){
        // get storeSales
        List<String> storeName = new ArrayList<>();
        List<Integer> storeSalesValue = new ArrayList<>();
        List<StoreSales> storeSales = storeProductMapper.selectStoreSales();
        for (StoreSales storeSale : storeSales) {
            String name = storeSale.getName();
            int value = storeSale.getValue();
            storeName.add(name);
            storeSalesValue.add(value);
        }
        JSONObject storeSalesJsonObject = new JSONObject();
        storeSalesJsonObject.put("storeName",storeName);
        storeSalesJsonObject.put("storeSalesValue",storeSalesValue);
        // get storeCategoryCount
        List<StoreCategoryCount> storeCategoryCounts = storeProductMapper.selectCateCountData();
        // get goodProduct
        GoodProduct goodProduct = storeProductMapper.selectGoodStore();
        int id = goodProduct.getId();
        StoreProduct storeProduct = storeProductService.getById(id);
        // Get hot prices
        BigDecimal price = storeProduct.getPrice();
        // Get products with 0 sales
        List<StoreProduct> salesZeroList = storeProductService.list(Wrappers.<StoreProduct>lambdaQuery().eq(StoreProduct::getSales,0));


        JSONObject lineJsonObject = new JSONObject();
        // get categoryNameList
        List<String> categoryNameList = storeProductMapper.getCategoryList();
        lineJsonObject.put("categoryNameList",categoryNameList);
        // get days
        List<String> days = storeProductMapper.getDays();
        Collections.reverse(days);
        lineJsonObject.put("days",days);
        //  Get the products classified under the menu, and count the sales in the last seven days
        List<StoreCategorySalesVo> storeCategorySalesVos = new ArrayList<>();
        for (String categoryName : categoryNameList) {
            StoreCategorySalesVo storeCategorySalesVo = new StoreCategorySalesVo();
            storeCategorySalesVo.setName(categoryName);
            storeCategorySalesVo.setType("line");
            List<Integer> dataList = new ArrayList<>();
            for (int i = 0; i < days.size(); i++) {
                StoreCategorySales storeCategorySales = storeProductMapper.getStoreCategorySales(days.get(i), categoryName);
                if (storeCategorySales !=null){
                    // Get the sales of the day, if there are sales, add
                    dataList.add(storeCategorySales.getNum());
                }else{
                    // No sales on the day, set to 0 sales
                    dataList.add(0);
                }
            }

            storeCategorySalesVo.setData(dataList);
            storeCategorySalesVos.add(storeCategorySalesVo);
        }
        lineJsonObject.put("storeCategorySalesVos",storeCategorySalesVos);


        JSONObject jsonObject = new JSONObject();
        // How many items are in the statistics menu
        jsonObject.put("storeCategoryCounts",storeCategoryCounts);
        // Count the sales of goods, and then count which products are popular
        jsonObject.put("storeSales",storeSalesJsonObject);
        // Provide suggested variables
        jsonObject.put("name",goodProduct.getName());
        jsonObject.put("categoryName",goodProduct.getCategoryName());
        jsonObject.put("salesNumber", goodProduct.getValue());
        jsonObject.put("price",price);

        // Statistics on the sales of products under the menu in the last 7 days
        jsonObject.put("lineJsonObject",lineJsonObject);

        // Statistical product sales have always been 0 products
        jsonObject.put("salesZeroList",salesZeroList);
        return AjaxResult.success(jsonObject);
    }

    /**
     * 查询商品列表列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreProduct storeProduct)
    {
        startPage();
        if (Objects.nonNull(storeProduct.getCateId())){
            if (storeProduct.getCateId() == 0){
                storeProduct.setCateId(null);
            }
        }

        List<StoreProduct> list = storeProductService.selectStoreProductList(storeProduct);
        for (StoreProduct product : list) {
            Long cateId = product.getCateId();
            StoreCategory category = storeCategoryService.selectStoreCategoryById(cateId);
            if (!Objects.isNull(category)){
                product.setCateName(category.getCateName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出商品列表列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:export')")
    @Log(title = "商品列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreProduct storeProduct)
    {
        List<StoreProduct> list = storeProductService.selectStoreProductList(storeProduct);
        ExcelUtil<StoreProduct> util = new ExcelUtil<StoreProduct>(StoreProduct.class);
        util.exportExcel(response, list, "商品列表Data");
    }

    /**
     * 获取商品列表详细信息
     */
//    @PreAuthorize("@ss.hasPermi('product:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(storeProductService.selectStoreProductById(id));
    }

    /**
     * 新增商品列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:add')")
    @Log(title = "商品列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreProduct storeProduct)
    {
        return toAjax(storeProductService.insertStoreProduct(storeProduct));
    }

    /**
     * 修改商品列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:edit')")
    @Log(title = "商品列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreProduct storeProduct)
    {
        return toAjax(storeProductService.updateStoreProduct(storeProduct));
    }

    /**
     * 删除商品列表
     */
//    @PreAuthorize("@ss.hasPermi('product:product:remove')")
    @Log(title = "商品列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storeProductService.deleteStoreProductByIds(ids));
    }
}
