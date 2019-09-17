package com.b2b.dubbo.order.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.OrderInfo;
import guo.ping.e3mall.common.pojo.E3Result;
import guo.ping.e3mall.order.pojo.OrderInfo;

public interface OrderService {
    Result createOrder(OrderInfo orderInfo);
}
