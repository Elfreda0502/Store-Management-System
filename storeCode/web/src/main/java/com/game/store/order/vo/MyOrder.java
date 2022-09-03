package com.game.store.order.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.game.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: RuoYi-Vue-master
 * @description: 我的订单
 * @author: Yu Yue
 * @create: 2022-05-22 17:19
 **/
@Data
public class MyOrder {

    private Long productId;
    private String storeInfo;
    private String id;
    private String productName;
    private String cartNum;
    private BigDecimal onePrice;
    private BigDecimal price;
    private String productImage;
    private Integer status;
    private String verifyCode;
    private Integer shippingType;

    @Excel(name = "快递名称/送货人姓名")
    private String deliveryName;


    @Excel(name = "发货类型")
    private String deliveryType;


    @Excel(name = "快递单号/手机号")
    private String deliveryId;


    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
