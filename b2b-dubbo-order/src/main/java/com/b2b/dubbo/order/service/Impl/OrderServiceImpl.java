package com.b2b.dubbo.order.service.Impl;

import com.b2b.dubbo.order.service.OrderService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.OrderInfo;
import com.b2b.mall.db.mapper.OrderItemMapper;
import com.b2b.mall.db.mapper.OrderMapper;
import com.b2b.mall.db.mapper.OrderShippingMapper;
import com.b2b.mall.db.model.OrderItem;
import com.b2b.mall.db.model.OrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author kiwi
 */
@Transactional
@Service(version = "${Dubbo_Version}")
public class OrderServiceImpl implements OrderService {

	@Autowired
    private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private Integer ORDER_ID_BEGIN;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;

	@Transient
	@Override
	public Result createOrder(OrderInfo orderInfo) {
		// 1、接收表单的数据
		// 2、生成订单id
		if (!redisTemplate.hasKey(ORDER_GEN_KEY)) {
			//设置初始值
			redisTemplate.opsForValue().set(ORDER_GEN_KEY, ORDER_ID_BEGIN);
		}
		String orderId = redisTemplate.opsForValue().increment(ORDER_GEN_KEY, 1L).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		orderInfo.setPaymentTime(date);
		orderInfo.setEndTime(date);
		orderInfo.setConsignTime(date);
		orderInfo.setCloseTime(date);
		orderInfo.setBuyerRate(5);
		// 3、向订单表插入数据。
		orderMapper.insert(orderInfo);
		// 4、向订单明细表插入数据
		List<OrderItem> orderItems = orderInfo.getOrderItems();
		for (OrderItem tbOrderItem : orderItems) {
			//生成明细id
			Long orderItemId = redisTemplate.opsForValue().increment(ORDER_ITEM_ID_GEN_KEY, 1L);
			tbOrderItem.setId(orderItemId.toString());
			tbOrderItem.setOrderId(orderId);
			//插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		// 5、向订单物流表插入数据。
		OrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		// 6、返回e3Result。
		return Result.ok(orderId);
	}
}