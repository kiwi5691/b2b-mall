package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.ShippingService;
import com.b2b.mall.common.util.Constant;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.OrderShippingMapper;
import com.b2b.mall.db.model.OrderShipping;
import com.b2b.mall.db.model.ResObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kiwi
 * @Date: 2019/6/26 13:09
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    @Resource
    private OrderShippingMapper orderShippingMapper;

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
        model.addAttribute("orderShippingList", orderShippingList);
        model.addAttribute("orderShipping", orderShipping);
    }

    @Override
    public ResObject<Object> shippingEditState(OrderShipping orderShipping) {
        orderShippingMapper.deleteByPrimaryKey(orderShipping.getOrderId());
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }
}
