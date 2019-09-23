package com.b2b.dubbo.order.service.Impl;

import com.b2b.dubbo.order.service.OrderInfoService;
import com.b2b.mall.db.mapper.OrderItemMapper;
import com.b2b.mall.db.mapper.OrderMapper;
import com.b2b.mall.db.mapper.OrderShippingMapper;
import com.b2b.mall.db.model.Order;
import com.b2b.mall.db.model.OrderItem;
import com.b2b.mall.db.model.OrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/23 10:38
 */
@Service(version = "${Dubbo_Version}")
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Override
    public List<Order> getOrderById(Long userId) {

        return orderMapper.getByUserId(userId);
    }

    @Override
    public OrderShipping getAddressByOrderId(String orderId) {
        return orderShippingMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(String orderId) {
        return orderItemMapper.selectByOrderId(orderId);
    }
}
