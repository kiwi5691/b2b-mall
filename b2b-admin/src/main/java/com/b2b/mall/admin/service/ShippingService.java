package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.OrderShipping;
import com.b2b.mall.db.model.ResObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:52
 */
public interface ShippingService {
    void orderManage(OrderShipping orderShipping,Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
    ResObject<Object> shippingEditState(OrderShipping orderShipping);
}
