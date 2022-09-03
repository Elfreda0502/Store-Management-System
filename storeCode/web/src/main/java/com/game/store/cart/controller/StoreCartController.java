package com.game.store.cart.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.game.common.core.domain.entity.SysUser;
import com.game.common.utils.DateUtils;
import com.game.common.utils.SecurityUtils;
import com.game.common.utils.poi.ExcelUtil;
import com.game.store.cart.domain.StoreCart;
import com.game.store.cart.service.IStoreCartService;
import com.game.store.cart.vo.StoreCartVo;
import com.game.store.product.domain.StoreProduct;
import com.game.store.product.service.IStoreProductService;
import com.game.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.game.common.annotation.Log;
import com.game.common.core.controller.BaseController;
import com.game.common.core.domain.AjaxResult;
import com.game.common.enums.BusinessType;

import com.game.common.core.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author Yu Yue
 * @date 2022-05-14
 */
@RestController
@RequestMapping("/store/cart")
public class StoreCartController extends BaseController
{
    @Autowired
    private IStoreCartService storeCartService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IStoreProductService storeProductService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('cart:cart:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreCart storeCart)
    {
        startPage();
        List<StoreCart> list = storeCartService.selectStoreCartList(storeCart);
        return getDataTable(list);
    }

    @GetMapping("/updateCartNum")
    public AjaxResult updateCartNum(@RequestParam("id") String id,@RequestParam("cartNum") int cartNum){
        StoreCart storeCart = storeCartService.getOne(Wrappers.<StoreCart>lambdaQuery().eq(StoreCart::getId,id));
        if(!ObjectUtils.isNull(storeCart)){
            int num = cartNum;
            storeCart.setCartNum(String.valueOf(num));
            storeCartService.updateStoreCart(storeCart);
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }

    }

    /**
     * 查询User购物车列表
     */
    @GetMapping("/userList")
    public TableDataInfo userList(StoreCart storeCart)
    {

        String username = SecurityUtils.getUsername();
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        Long userId = sysUser.getUserId();
        List<StoreCart> list = storeCartService.list(Wrappers.<StoreCart>lambdaQuery().eq(StoreCart::getUid, userId));
        List<StoreCartVo> voList = new ArrayList<>();
        for (StoreCart cart : list) {
            StoreCartVo storeCartVo = new StoreCartVo();
            Long productId = Long.valueOf(cart.getProductId());
            StoreProduct storeProduct = storeProductService.getById(productId);
            String sockState = "";
            if (Integer.parseInt(storeProduct.getStock())>=Integer.parseInt(cart.getCartNum())){
                sockState = "有货";
            }else{
                sockState = "无货";
            }
            Long cartNum = Long.valueOf(cart.getCartNum());
//            Long productId = storeProduct.getId();
            voList.add(new StoreCartVo(String.valueOf(cart.getId()),storeProduct.getImage(),storeProduct.getStoreName(),storeProduct.getPrice(),sockState,cartNum,false,productId));
        }
        return getDataTable(voList);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('cart:cart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreCart storeCart)
    {
        List<StoreCart> list = storeCartService.selectStoreCartList(storeCart);
        ExcelUtil<StoreCart> util = new ExcelUtil<StoreCart>(StoreCart.class);
        util.exportExcel(response, list, "购物车Data");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('cart:cart:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(storeCartService.selectStoreCartById(id));
    }

    /**
     * 新增购物车
     */
//    @PreAuthorize("@ss.hasPermi('cart:cart:add')")
//    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreCart storeCart)
    {
//        String username = SecurityUtils.getUsername();
//        SysUser sysUser = sysUserService.selectUserByUserName(username);
//        Long userId = sysUser.getUserId();
//        storeCart.setUid(String.valueOf(userId));
//        return toAjax(storeCartService.insertStoreCart(storeCart));

        String username = SecurityUtils.getUsername();
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        Long userId = sysUser.getUserId();
        storeCart.setUid(String.valueOf(userId));
        StoreCart getStoreCart = storeCartService.getOne(Wrappers.<StoreCart>lambdaQuery().eq(StoreCart::getUid, userId).eq(StoreCart::getProductId, storeCart.getProductId()));

        if (ObjectUtils.isNull(getStoreCart)){
            storeCart.setCreateTime(DateUtils.getNowDate());
            storeCart.setCartNum("1");
            return toAjax(storeCartService.save(storeCart));
        }else{
            getStoreCart.setUpdateTime(DateUtils.getNowDate());
            String cartNum = getStoreCart.getCartNum();
//            getStoreCart.setCartNum(String.valueOf(Integer.parseInt(cartNum) + 1));
            getStoreCart.setCartNum(String.valueOf(Integer.parseInt(cartNum))); 
            return toAjax(storeCartService.updateStoreCart(getStoreCart));
        }
    }
    /**
     * 新增或更新购物车
     */
//    @PreAuthorize("@ss.hasPermi('cart:cart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping("addOrUpdate")
    public AjaxResult addOrUpdate(@RequestBody StoreCart storeCart)
    {
        String username = SecurityUtils.getUsername();
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        Long userId = sysUser.getUserId();
        storeCart.setUid(String.valueOf(userId));
        StoreCart getStoreCart = storeCartService.getOne(Wrappers.<StoreCart>lambdaQuery().eq(StoreCart::getUid, userId).eq(StoreCart::getProductId, storeCart.getProductId()));
        if (ObjectUtils.isNull(getStoreCart)){
            storeCart.setCartNum("1");
            return toAjax(storeCartService.save(storeCart));
        }else{
            String cartNum = getStoreCart.getCartNum();
//            getStoreCart.setCartNum(String.valueOf(Integer.parseInt(cartNum) + 1));
            
            getStoreCart.setCartNum(String.valueOf(Integer.parseInt(cartNum)));
            return toAjax(storeCartService.updateStoreCart(getStoreCart));
        }

    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('cart:cart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreCart storeCart)
    {
        return toAjax(storeCartService.updateStoreCart(storeCart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('cart:cart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storeCartService.deleteStoreCartByIds(ids));
    }

    /**
     * 删除购物车
     */

    @GetMapping("/del/{ids}")
    public AjaxResult remove2(@PathVariable Long[] ids)
    {
        return toAjax(storeCartService.deleteStoreCartByIds(ids));
    }
}
