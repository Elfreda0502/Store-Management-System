package com.game.store.order.param.dto;

import lombok.Data;

/**
 * @program: RuoYi-Vue-master
 * @description: 订单退款申请
 * @author: Yu Yue
 * @create: 2022-05-22 21:02
 **/
@Data
public class StoreOrderRefund {
    private String orderId;
    private String refundReason;
}
