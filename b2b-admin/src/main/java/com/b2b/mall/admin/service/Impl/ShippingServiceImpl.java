package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.ShippingService;
import com.b2b.mall.common.redis.RedisManager;
import com.b2b.mall.common.util.Constant;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.DeliveryMapper;
import com.b2b.mall.db.mapper.OrderMapper;
import com.b2b.mall.db.mapper.OrderShippingMapper;
import com.b2b.mall.db.model.Delivery;
import com.b2b.mall.db.model.Order;
import com.b2b.mall.db.model.OrderShipping;
import com.b2b.mall.db.model.ResObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kiwi
 * @Date: 2019/6/26 13:09
 */
@Slf4j
@Service
public class ShippingServiceImpl implements ShippingService {
    @Resource
    private OrderShippingMapper orderShippingMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisManager redisManager;

    @Resource
    private DeliveryMapper deliveryMapper;

    @Override
    public void orderManage(OrderShipping orderShipping, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }
        int rows = orderShippingMapper.selectAll().size();
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        orderShipping.setStart((pageCurrent - 1) * pageSize);
        orderShipping.setEnd(pageSize);
        List<OrderShipping> orderShippingList = orderShippingMapper.selectAll();
        for (OrderShipping orderShipping1 : orderShippingList){
            orderShipping1.setCreatedStr(DateUtil.getDateStr(orderShipping1.getCreated()));
            orderShipping1.setUpdatedStr(DateUtil.getDateStr(orderShipping1.getUpdated()));
        }
        String pageHTML = PageUtil.getPageContent("shippingManage_{pageCurrent}_{pageSize}_{pageCount}?orderId=" + orderShipping.getOrderId() + "&receiverName" + orderShipping.getReceiverName() + "&receiverPhone" + orderShipping.getReceiverPhone() + "&receiverMobile" + orderShipping.getReceiverMobile() + "&receiverState" + orderShipping.getReceiverState()+"&receiverCity"+orderShipping.getReceiverCity()+"&receiverDistrict"+orderShipping.getReceiverDistrict()+"&receiverAddress"+orderShipping.getReceiverAddress()+"&receiverZip"+orderShipping.getReceiverZip()+"&created"+orderShipping.getCreated()+"&updated"+orderShipping.getUpdated(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("orderShippingList", orderShippingList);
        model.addAttribute("orderShipping", orderShipping);
    }


    @Override
    public void sendOffGet(Model model, OrderShipping orderShipping) {

        List<Delivery> DeliveryCompsAll =null;
        DeliveryCompsAll =deliveryMapper.selectAll();

        List<String> delieryCompes=null;

        delieryCompes = DeliveryCompsAll.stream().map(Delivery::getDeliveryName).collect(Collectors.toList());
        OrderShipping orderShipping1 = null;

        orderShipping1=orderShippingMapper.selectByPrimaryKey(orderShipping.getOrderId());
        orderShipping1.setCreatedStr(DateUtil.getDateStr(orderShipping1.getCreated()));
        orderShipping1.setUpdatedStr(DateUtil.getDateStr(orderShipping1.getUpdated()));

        model.addAttribute("orderShipping", orderShipping1);
        model.addAttribute("delieryCompes",delieryCompes);
    }

    @Override
    public void sendOffPost(Model model, OrderShipping orderShipping,String delieryCompes,String sendOffId) {

        log.info("temp os"+delieryCompes+"" +sendOffId);
        Order order = new Order();
        order.setOrderId(orderShipping.getOrderId());
        order.setStatus(4);
        order.setConsignTime(new Date());
        order.setShippingName(delieryCompes);
        order.setShippingCode(sendOffId);
        orderMapper.updateByShippingState(order);

        orderShippingMapper.deleteByPrimaryKey(orderShipping.getOrderId());
    }
}
