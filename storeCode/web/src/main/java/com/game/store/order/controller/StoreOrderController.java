package com.game.store.order.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.game.common.core.domain.entity.SysUser;
import com.game.common.exception.ServiceException;
import com.game.common.utils.SecurityUtils;
import com.game.common.utils.uuid.IdUtils;
import com.game.store.cart.domain.StoreCart;
import com.game.store.cart.service.IStoreCartService;
import com.game.store.category.service.IStoreCategoryService;
import com.game.store.order.domain.StoreOrder;
import com.game.store.order.param.dto.CreateOrderDto;
import com.game.store.order.param.dto.StoreOrderRefund;
import com.game.store.order.service.IStoreOrderService;
import com.game.store.order.vo.MyOrder;
import com.game.store.product.domain.StoreProduct;
import com.game.store.product.service.IStoreProductService;
import com.game.system.service.ISysUserService;
import com.game.user.domain.UserAddress;
import com.game.store.service.IUserAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.game.common.annotation.Log;
import com.game.common.core.controller.BaseController;
import com.game.common.core.domain.AjaxResult;
import com.game.common.enums.BusinessType;

import com.game.common.utils.poi.ExcelUtil;
import com.game.common.core.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author Yu Yue
 * @date 2022-05-22
 */
@RestController
@RequestMapping("/store/order")
public class StoreOrderController extends BaseController
{
    @Autowired
    private IStoreOrderService storeOrderService;

    @Autowired
    private IStoreCartService storeCartService;

    @Autowired
    private IStoreProductService storeProductService;

    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IStoreCategoryService storeCategoryService;

    /**
     * 后台
     * 查询订单列表
     */
//    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreOrder storeOrder)
    {
        startPage();
        List<StoreOrder> list = storeOrderService.selectStoreOrderList(storeOrder);
        return getDataTable(list);
    }
    @GetMapping("/orderOk")
    public AjaxResult orderOk(@RequestParam String orderId){
        StoreOrder storeOrder = storeOrderService.selectStoreOrderById(orderId);
        storeOrder.setStatus(3);
        storeOrderService.updateStoreOrder(storeOrder);
        return AjaxResult.success();
    }


    /**
     * 前台
     * 申请退款
     */
    @PostMapping("/requestRefund")
    public AjaxResult requestRefund(@RequestBody StoreOrderRefund param)
    {
//        startPage();
//        List<StoreOrder> list = storeOrderService.selectStoreOrderList(storeOrder);
//        return getDataTable(list);

        String orderId = param.getOrderId();
        StoreOrder storeOrder = storeOrderService.selectStoreOrderById(orderId);

        Integer status = storeOrder.getStatus();
//        Date payTime = storeOrder.getPayTime();
//        long afterTime = payTime.getTime() + 7200000L;
//        Date date = new Date();
//        long nowTime = date.getTime();
//        if (afterTime - nowTime < 0){
//            throw new ServiceException("订单已超过2小时，无法退款");
//        }
        String productId = String.valueOf(storeOrder.getProductId());
        String totalNum = storeOrder.getTotalNum();
        StoreProduct storeProduct = storeProductService.getById(productId);
        if (status.equals(0)){


            String stock = String.valueOf(Integer.parseInt(totalNum) + Integer.parseInt(storeProduct.getStock()));
            storeProduct.setStock(stock);
            storeProductService.updateStoreProduct(storeProduct);

            Long userId = SecurityUtils.getUserId();
            SysUser sysUser = sysUserService.selectUserById(userId);
            sysUser.setMoney(sysUser.getMoney().add(storeProduct.getPrice().multiply(new BigDecimal(totalNum))));
            sysUserService.updateUser(sysUser);

            storeOrder.setStatus(-3);
            storeOrder.setRefundStatus(String.valueOf(2));
//            storeOrderService.deleteStoreOrderById(orderId);
            storeOrderService.updateStoreOrder(storeOrder);
        }else if (status.equals(1)){
            storeOrder.setRefundReasonWapExplain(param.getRefundReason());
            storeOrder.setRefundReasonTime(new Date());
            BigDecimal price = storeProduct.getPrice();
            storeOrder.setRefundPrice(price.multiply(new BigDecimal(totalNum)));
            storeOrder.setRefundStatus("1");
//            storeOrder.setStatus(-1);
            storeOrderService.updateStoreOrder(storeOrder);
        }
        return AjaxResult.success();
    }

    /**
     * 前台
     * 查询我的订单
     */
//    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/myOrderList")
    public TableDataInfo myOrderList(StoreOrder storeOrder)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        storeOrder.setUid(String.valueOf(userId));
        List<MyOrder> myOrderList = new ArrayList<>();
        List<StoreOrder> list = storeOrderService.selectStoreOrderList(storeOrder);
        for (StoreOrder order : list) {
            MyOrder myOrder = new MyOrder();
            BeanUtils.copyProperties(order,myOrder);
            String cartId = order.getCartId();
            StoreCart storeCart = storeCartService.getById(cartId);
            String cartNum = order.getTotalNum();
            String productId =String.valueOf(order.getProductId());
            StoreProduct storeProduct = storeProductService.getById(productId);
            myOrder.setOnePrice(storeProduct.getPrice());
            myOrder.setProductId(storeProduct.getId());
            String image = storeProduct.getImage();
            BigDecimal payPrice = order.getPayPrice();
            String storeName = storeProduct.getStoreName();
            myOrder.setProductName(storeName);
            myOrder.setPrice(payPrice);
            myOrder.setStoreInfo(storeProduct.getDescription());
            myOrder.setProductImage(image);
            myOrder.setCartNum(cartNum);
            myOrderList.add(myOrder);
        }
        return getDataTable(myOrderList);
    }

    /**
     * 创建订单
     */
    @PostMapping("/createOrder")
    public AjaxResult createOrder(@RequestBody CreateOrderDto createOrderDto)
    {
        Long userId = SecurityUtils.getUserId();
        String userAddressId = createOrderDto.getAddressId();
        List<String> cartIds = createOrderDto.getCartId();
        for (String cartId : cartIds) {
            StoreCart storeCart = storeCartService.getById(cartId);

            int cartNum = Integer.parseInt(storeCart.getCartNum());
            String productId = storeCart.getProductId();
//            StoreProduct storeProduct = storeProductService.getById(productId);
//            List<StoreOrder> storeOrders = storeOrderService.selectStoreOrderList(new StoreOrder(Long.valueOf(productId),String.valueOf(userId)));
//            if ( storeOrders.size() > 0){
//                throw new ServiceException("该游戏已购买，无法再次购买");
//            }
            StoreProduct storeProduct = storeProductService.getById(productId);
            int sales = Integer.parseInt(storeProduct.getSales()) + cartNum;
            storeProduct.setSales(String.valueOf(sales));
            storeProductService.updateStoreProduct(storeProduct);
            int stock = Integer.parseInt(storeProduct.getStock());
            BigDecimal price = storeProduct.getPrice();
            if (cartNum > stock){
                throw new ServiceException("Insufficient stock, failed to create order");
            }else{

                BigDecimal payPrice = price.multiply(new BigDecimal(cartNum));
//                Long userId = SecurityUtils.getLoginUser().getUserId();
                SysUser user = sysUserService.selectUserById(userId);
                BigDecimal userMoney =user.getMoney();
                if (userMoney.compareTo(payPrice) <= 0){
                    throw new ServiceException("User balance is insufficient, creating order failed");
                }

                UserAddress userAddress = userAddressService.selectUserAddressById(Long.valueOf(userAddressId));
                if (userAddress == null){
                    throw new ServiceException("The delivery address does not exist, please select the delivery address");
                }
                String address = userAddress.getAddress();

                String consignee = userAddress.getConsignee();

                String receivingNumber = userAddress.getReceivingNumber();
                StoreOrder storeOrder = new StoreOrder();
                String orderId = IdUtils.fastUUID();
                storeOrder.setOrderId(orderId);
                storeOrder.setCartId(cartId);
                storeOrder.setUserAddress(address);
                storeOrder.setUserPhone(receivingNumber);
                storeOrder.setRealName(consignee);
                storeOrder.setTotalNum(storeCart.getCartNum());
                storeOrder.setPayPrice(payPrice);
                storeOrder.setUid(String.valueOf(user.getUserId()));
                storeOrder.setCost(storeProduct.getCost());
                storeOrder.setTotalPrice(payPrice);
                storeOrder.setShippingType(Integer.parseInt(createOrderDto.getShippingType()));
                storeOrder.setPaid("1");
                storeOrder.setPayTime(new Date());
                storeOrder.setProductId(Long.valueOf(productId));
                storeOrder.setStoreId(storeProduct.getCateId());
//                storeProduct
                int flag = storeOrderService.insertStoreOrder(storeOrder);
                if (flag>0){

                    BigDecimal money = user.getMoney().subtract(payPrice);
                    user.setMoney(money);
                    sysUserService.updateUser(user);

                    int stockAfter = Integer.parseInt(storeProduct.getStock()) - cartNum;
                    storeProduct.setStock(String.valueOf(stockAfter));
                    storeProductService.updateStoreProduct(storeProduct);
                }

            }
        }
        return AjaxResult.success();
    }

    @GetMapping("/refundFail")
    public AjaxResult refundFail(@RequestParam("id") String id){
        StoreOrder storeOrder = storeOrderService.selectStoreOrderById(id);
        storeOrder.setRefundStatus("0");
        storeOrderService.updateStoreOrder(storeOrder);
        return AjaxResult.success();
    }

    /**
     * 退款
     * @param id
     * @return
     */
    @GetMapping("/refund")
    public AjaxResult refund(@RequestParam("id") String id){
        StoreOrder storeOrder = storeOrderService.selectStoreOrderById(id);
        storeOrder.setStatus(-3);
        storeOrder.setRefundStatus("2");
        storeOrderService.updateStoreOrder(storeOrder);
        String productId = String.valueOf(storeOrder.getProductId());
        String totalNum = storeOrder.getTotalNum();
        StoreProduct storeProduct = storeProductService.getById(productId);

        String stock = String.valueOf(Integer.parseInt(totalNum) + Integer.parseInt(storeProduct.getStock()));
        storeProduct.setStock(stock);
        storeProductService.updateStoreProduct(storeProduct);

        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserService.selectUserById(userId);
        sysUser.setMoney(sysUser.getMoney().add(storeProduct.getPrice().multiply(new BigDecimal(totalNum))));
        sysUserService.updateUser(sysUser);
        return AjaxResult.success();
    }

    /**
     * ship
     */
    @PostMapping("/deliverGoods")
    public AjaxResult deliverGoods(@RequestBody StoreOrder storeOrder)
    {
        storeOrder.setStatus(1);
        return toAjax(storeOrderService.updateStoreOrder(storeOrder));
    }



    /**
     * 导出订单列表
     */
//    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreOrder storeOrder)
    {
        List<StoreOrder> list = storeOrderService.selectStoreOrderList(storeOrder);
        ExcelUtil<StoreOrder> util = new ExcelUtil<StoreOrder>(StoreOrder.class);
        util.exportExcel(response, list, "订单Data");
    }

    /**
     * 获取订单详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(storeOrderService.selectStoreOrderById(id));
    }

    /**
     * 新增订单
     */
//    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreOrder storeOrder)
    {
        return toAjax(storeOrderService.insertStoreOrder(storeOrder));
    }

    /**
     * 修改订单
     */
//    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreOrder storeOrder)
    {
        return toAjax(storeOrderService.updateStoreOrder(storeOrder));
    }

    /**
     * 删除订单
     */
//    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(storeOrderService.deleteStoreOrderByIds(ids));
    }
}
