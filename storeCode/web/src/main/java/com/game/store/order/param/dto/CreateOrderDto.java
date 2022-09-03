package com.game.store.order.param.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: RuoYi-Vue-master
 * @description: 创建订单Dto
 * @author: Yu Yue
 * @create: 2022-05-22 14:34
 **/
@Data
public class CreateOrderDto {
    private String addressId;
    private List<String> cartId;
    private String shippingType;
}
