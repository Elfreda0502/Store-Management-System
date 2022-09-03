package com.game.store.product.domain;

import lombok.Data;

/**
 * @program: storeCode
 * @description: GoodProduct
 * @author: BigSmart
 * @create: 2022-07-13 16:28
 **/
@Data
public class GoodProduct {
    private int id;
    private String name;
    private int value;
    private String categoryName;
}
