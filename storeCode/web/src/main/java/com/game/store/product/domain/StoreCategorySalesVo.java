package com.game.store.product.domain;

import lombok.Data;

import java.util.List;

/**
 * @program: storeCode
 * @description: StoreCategorySalesVo
 * @author: BigSmart
 * @create: 2022-07-14 12:42
 **/
@Data
public class StoreCategorySalesVo {
    private String name;
    private String type;
//    private String stack;
    private List<Integer> data;
}
