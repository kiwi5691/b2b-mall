package com.b2b.dubbo.order.service;

import com.b2b.mall.db.model.Order;
import com.b2b.mall.db.model.OrderItem;
import com.b2b.mall.db.model.OrderShipping;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/23 10:35
 */
public interface OrderInfoService {
    public List<Order> getOrderById(Long userId);
    public OrderShipping getAddressByOrderId(String orderId);
    public List<OrderItem> getOrderItemByOrderId(String orderId);
}
