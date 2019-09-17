package com.b2b.dubbo.order.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.OrderInfo;

public interface OrderService {
    Result createOrder(OrderInfo orderInfo);
}
