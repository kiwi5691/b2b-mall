package com.b2b.mall.db.entity;

import com.b2b.mall.db.model.Order;
import com.b2b.mall.db.model.OrderItem;
import com.b2b.mall.db.model.OrderShipping;

import java.io.Serializable;
import java.util.List;

public class OrderInfo extends Order implements Serializable {
    private List<OrderItem> orderItems;
	private OrderShipping orderShipping;
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
