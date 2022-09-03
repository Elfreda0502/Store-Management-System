package com.game.store.cart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: RuoYi-Vue-master
 * @description: User购物车列表
 * @author: Yu Yue
 * @create: 2022-05-15 13:35
 **/
@Data
public class StoreCartVo {
    private String id;
    private String image;
    private String storeName;
    private BigDecimal price;
    private String stockText;
    private Long cartNum;
    private Boolean check;
    private Long productId;

    public StoreCartVo() {
    }

    public StoreCartVo(String id, String image, String storeName, BigDecimal price, String stockText, Long cartNum, Boolean check, Long productId) {
        this.id = id;
        this.image = image;
        this.storeName = storeName;
        this.price = price;
        this.stockText = stockText;
        this.cartNum = cartNum;
        this.check = check;
        this.productId = productId;
    }
    //
//    public StoreCartVo(String id,String image,String storeName, BigDecimal price, String stockText, Long cartNum,Boolean check) {
//        this.id = id;
//        this.image = image;
//        this.storeName = storeName;
//        this.price = price;
//        this.stockText = stockText;
//        this.cartNum = cartNum;
//        this.check = check;
//    }
}
