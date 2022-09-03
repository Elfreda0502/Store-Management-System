package com.game.store.category.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.game.store.category.domain.StoreCategory;
import com.game.store.category.service.IStoreCategoryService;
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
import com.game.common.utils.poi.ExcelUtil;

/**
 * 商品分类Controller
 *
 * @author Yu Yue
 * @date 2022-05-13
 */
@RestController
@RequestMapping("/store/category")
public class StoreCategoryController extends BaseController
{
    @Autowired
    private IStoreCategoryService storeCategoryService;

    /**
     * 查询商品分类列表
     */
//    @PreAuthorize("@ss.hasPermi('system:category:list')")
    @GetMapping("/list")
    public AjaxResult list(StoreCategory storeCategory)
    {
        List<StoreCategory> list = storeCategoryService.selectStoreCategoryList(storeCategory);
        return AjaxResult.success(list);
    }

    /**
     * 导出商品分类列表
     */
//    @PreAuthorize("@ss.hasPermi('system:category:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreCategory storeCategory)
    {
        List<StoreCategory> list = storeCategoryService.selectStoreCategoryList(storeCategory);
        ExcelUtil<StoreCategory> util = new ExcelUtil<StoreCategory>(StoreCategory.class);
        util.exportExcel(response, list, "商品分类Data");
    }

    /**
     * 获取商品分类详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(storeCategoryService.selectStoreCategoryById(id));
    }

    /**
     * 新增商品分类
     */
//    @PreAuthorize("@ss.hasPermi('system:category:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreCategory storeCategory)
    {
        return toAjax(storeCategoryService.insertStoreCategory(storeCategory));
    }

    /**
     * 修改商品分类
     */
//    @PreAuthorize("@ss.hasPermi('system:category:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreCategory storeCategory)
    {
        return toAjax(storeCategoryService.updateStoreCategory(storeCategory));
    }

    /**
     * 删除商品分类
     */
//    @PreAuthorize("@ss.hasPermi('system:category:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storeCategoryService.deleteStoreCategoryByIds(ids));
    }
}
