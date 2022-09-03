package com.game.store.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.game.common.annotation.Excel;
import com.game.common.core.domain.BaseEntity;

/**
 * 订单对象 store_order
 *
 * @author Yu Yue
 * @date 2022-05-22
 */
public class StoreOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    private String id;


    @Excel(name = "订单号")
    private String orderId;


    @Excel(name = "额外订单号")
    private String extendOrderId;


    @Excel(name = "Userid")
    private String uid;

    private Long productId;


    @Excel(name = "User姓名")
    private String realName;


    @Excel(name = "User电话")
    private String userPhone;


    @Excel(name = "详细地址")
    private String userAddress;


    @Excel(name = "购物车id")
    private String cartId;


    @Excel(name = "运费金额")
    private BigDecimal freightPrice;


    @Excel(name = "订单商品总数")
    private String totalNum;


    @Excel(name = "订单总价")
    private BigDecimal totalPrice;


    @Excel(name = "邮费")
    private BigDecimal totalPostage;


    @Excel(name = "实际支付金额")
    private BigDecimal payPrice;


    @Excel(name = "支付邮费")
    private BigDecimal payPostage;


    @Excel(name = "抵扣金额")
    private BigDecimal deductionPrice;


    @Excel(name = "优惠券id")
    private String couponId;


    @Excel(name = "优惠券金额")
    private BigDecimal couponPrice;


    @Excel(name = "支付 State")
    private String paid;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;


    @Excel(name = "支付方式")
    private String payType;


    @Excel(name = "订单 State", readConverterExp = "-=1,:=,申=请退款,-=2,:=,退=货成功,0=：待发货；1：待收货；2：已收货；3：已完成；-3：已退款")
    private Integer status;


    @Excel(name = "0 未退款 1 申请中 2 已退款")
    private String refundStatus;


    @Excel(name = "退款图片")
    private String refundReasonWapImg;


    @Excel(name = "退款User说明")
    private String refundReasonWapExplain;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date refundReasonTime;


    @Excel(name = "前台退款原因")
    private String refundReasonWap;


    @Excel(name = "不退款的理由")
    private String refundReason;


    @Excel(name = "退款金额")
    private BigDecimal refundPrice;


    @Excel(name = "快递公司编号")
    private String deliverySn;


    @Excel(name = "快递名称/送货人姓名")
    private String deliveryName;


    @Excel(name = "发货类型")
    private String deliveryType;


    @Excel(name = "快递单号/手机号")
    private String deliveryId;


    @Excel(name = "成本价")
    private BigDecimal cost;


    @Excel(name = "核销码")
    private String verifyCode;


    @Excel(name = "门店id")
    private Long storeId;


    @Excel(name = "配送方式 1=快递 ，2=门店自提")
    private Integer shippingType;

    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public StoreOrder() {
    }

    public StoreOrder(Long productId,String uid) {
        this.productId = productId;
        this.uid = uid;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setExtendOrderId(String extendOrderId)
    {
        this.extendOrderId = extendOrderId;
    }

    public String getExtendOrderId()
    {
        return extendOrderId;
    }
    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getUid()
    {
        return uid;
    }
    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getRealName()
    {
        return realName;
    }
    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

    public String getUserPhone()
    {
        return userPhone;
    }
    public void setUserAddress(String userAddress)
    {
        this.userAddress = userAddress;
    }

    public String getUserAddress()
    {
        return userAddress;
    }
    public void setCartId(String cartId)
    {
        this.cartId = cartId;
    }

    public String getCartId()
    {
        return cartId;
    }
    public void setFreightPrice(BigDecimal freightPrice)
    {
        this.freightPrice = freightPrice;
    }

    public BigDecimal getFreightPrice()
    {
        return freightPrice;
    }
    public void setTotalNum(String totalNum)
    {
        this.totalNum = totalNum;
    }

    public String getTotalNum()
    {
        return totalNum;
    }
    public void setTotalPrice(BigDecimal totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice()
    {
        return totalPrice;
    }
    public void setTotalPostage(BigDecimal totalPostage)
    {
        this.totalPostage = totalPostage;
    }

    public BigDecimal getTotalPostage()
    {
        return totalPostage;
    }
    public void setPayPrice(BigDecimal payPrice)
    {
        this.payPrice = payPrice;
    }

    public BigDecimal getPayPrice()
    {
        return payPrice;
    }
    public void setPayPostage(BigDecimal payPostage)
    {
        this.payPostage = payPostage;
    }

    public BigDecimal getPayPostage()
    {
        return payPostage;
    }
    public void setDeductionPrice(BigDecimal deductionPrice)
    {
        this.deductionPrice = deductionPrice;
    }

    public BigDecimal getDeductionPrice()
    {
        return deductionPrice;
    }
    public void setCouponId(String couponId)
    {
        this.couponId = couponId;
    }

    public String getCouponId()
    {
        return couponId;
    }
    public void setCouponPrice(BigDecimal couponPrice)
    {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getCouponPrice()
    {
        return couponPrice;
    }
    public void setPaid(String paid)
    {
        this.paid = paid;
    }

    public String getPaid()
    {
        return paid;
    }
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus()
    {
        return refundStatus;
    }
    public void setRefundReasonWapImg(String refundReasonWapImg)
    {
        this.refundReasonWapImg = refundReasonWapImg;
    }

    public String getRefundReasonWapImg()
    {
        return refundReasonWapImg;
    }
    public void setRefundReasonWapExplain(String refundReasonWapExplain)
    {
        this.refundReasonWapExplain = refundReasonWapExplain;
    }

    public String getRefundReasonWapExplain()
    {
        return refundReasonWapExplain;
    }
    public void setRefundReasonTime(Date refundReasonTime)
    {
        this.refundReasonTime = refundReasonTime;
    }

    public Date getRefundReasonTime()
    {
        return refundReasonTime;
    }
    public void setRefundReasonWap(String refundReasonWap)
    {
        this.refundReasonWap = refundReasonWap;
    }

    public String getRefundReasonWap()
    {
        return refundReasonWap;
    }
    public void setRefundReason(String refundReason)
    {
        this.refundReason = refundReason;
    }

    public String getRefundReason()
    {
        return refundReason;
    }
    public void setRefundPrice(BigDecimal refundPrice)
    {
        this.refundPrice = refundPrice;
    }

    public BigDecimal getRefundPrice()
    {
        return refundPrice;
    }
    public void setDeliverySn(String deliverySn)
    {
        this.deliverySn = deliverySn;
    }

    public String getDeliverySn()
    {
        return deliverySn;
    }
    public void setDeliveryName(String deliveryName)
    {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryName()
    {
        return deliveryName;
    }
    public void setDeliveryType(String deliveryType)
    {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType()
    {
        return deliveryType;
    }
    public void setDeliveryId(String deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryId()
    {
        return deliveryId;
    }

    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }

    public BigDecimal getCost()
    {
        return cost;
    }
    public void setVerifyCode(String verifyCode)
    {
        this.verifyCode = verifyCode;
    }

    public String getVerifyCode()
    {
        return verifyCode;
    }
    public void setStoreId(Long storeId)
    {
        this.storeId = storeId;
    }

    public Long getStoreId()
    {
        return storeId;
    }
    public void setShippingType(Integer shippingType)
    {
        this.shippingType = shippingType;
    }

    public Integer getShippingType()
    {
        return shippingType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("extendOrderId", getExtendOrderId())
            .append("uid", getUid())
            .append("realName", getRealName())
            .append("userPhone", getUserPhone())
            .append("userAddress", getUserAddress())
            .append("cartId", getCartId())
            .append("freightPrice", getFreightPrice())
            .append("totalNum", getTotalNum())
            .append("totalPrice", getTotalPrice())
            .append("totalPostage", getTotalPostage())
            .append("payPrice", getPayPrice())
            .append("payPostage", getPayPostage())
            .append("deductionPrice", getDeductionPrice())
            .append("couponId", getCouponId())
            .append("couponPrice", getCouponPrice())
            .append("paid", getPaid())
            .append("payTime", getPayTime())
            .append("payType", getPayType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .append("refundStatus", getRefundStatus())
            .append("refundReasonWapImg", getRefundReasonWapImg())
            .append("refundReasonWapExplain", getRefundReasonWapExplain())
            .append("refundReasonTime", getRefundReasonTime())
            .append("refundReasonWap", getRefundReasonWap())
            .append("refundReason", getRefundReason())
            .append("refundPrice", getRefundPrice())
            .append("deliverySn", getDeliverySn())
            .append("deliveryName", getDeliveryName())
            .append("deliveryType", getDeliveryType())
            .append("deliveryId", getDeliveryId())
            .append("cost", getCost())
            .append("verifyCode", getVerifyCode())
            .append("storeId", getStoreId())
            .append("shippingType", getShippingType())
            .toString();
    }
}
