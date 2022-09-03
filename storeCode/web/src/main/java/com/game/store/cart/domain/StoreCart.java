package com.game.store.cart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.game.common.annotation.Excel;

import java.util.Date;

/**
 * 购物车对象 store_cart
 *
 * @author Yu Yue
 * @date 2022-05-14
 */
@Data
public class StoreCart
{
//    private static final long serialVersionUID = 1L;


    private Long id;


    @Excel(name = "UserID")
    private String uid;


    @Excel(name = "商品ID")
    private String productId;


    @Excel(name = "商品数量")
    private String cartNum;


    @Excel(name = "购买 State")
    private Integer isPay;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
