package com.game.store.purchase.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.game.store.purchase.domain.Purchase;
import com.game.store.purchase.service.IPurchaseService;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.game.common.core.page.TableDataInfo;

/**
 * 采购Controller
 * 
 * @author store
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/store/purchase")
public class PurchaseController extends BaseController
{
    @Autowired
    private IPurchaseService purchaseService;

    /**
     * 查询采购列表
     */
    @PreAuthorize("@ss.hasPermi('system:purchase:list')")
    @GetMapping("/list")
    public TableDataInfo list(Purchase purchase)
    {
        startPage();
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        return getDataTable(list);
    }

    /**
     * 导出采购列表
     */
    @PreAuthorize("@ss.hasPermi('system:purchase:export')")
    @Log(title = "采购", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Purchase purchase)
    {
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        util.exportExcel(response, list, "采购Data");
    }

    /**
     * 获取采购详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:purchase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(purchaseService.selectPurchaseById(id));
    }

    /**
     * 新增采购
     */
    @PreAuthorize("@ss.hasPermi('system:purchase:add')")
    @Log(title = "采购", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Purchase purchase)
    {
        return toAjax(purchaseService.insertPurchase(purchase));
    }

    /**
     * 修改采购
     */
    @PreAuthorize("@ss.hasPermi('system:purchase:edit')")
    @Log(title = "采购", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Purchase purchase)
    {
        return toAjax(purchaseService.updatePurchase(purchase));
    }

    /**
     * 删除采购
     */
    @PreAuthorize("@ss.hasPermi('system:purchase:remove')")
    @Log(title = "采购", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(purchaseService.deletePurchaseByIds(ids));
    }
}
