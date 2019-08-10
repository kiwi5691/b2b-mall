package com.b2b.mall.admin.controller;


import com.b2b.mall.admin.service.ShippingService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.*;
import com.b2b.mall.db.model.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 物流管理
 */
@Controller
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @RequestMapping("/user/shippingManage_{pageCurrent}_{pageSize}_{pageCount}")
    @RequiresPermissions(value ="shippingManage")
    public String orderManage(OrderShipping orderShipping, @PathVariable Integer pageCurrent,
                              @PathVariable Integer pageSize,
                              @PathVariable Integer pageCount,
                              Model model) {
        shippingService.orderManage(orderShipping,pageCurrent,pageSize,pageCount,model);
        return "order/shippingManage";
    }

    @ResponseBody
    @PostMapping("/user/shippingEditState")
    public ResObject<Object> shippingEditState(OrderShipping orderShipping){
        return shippingService.shippingEditState(orderShipping);
    }
}
