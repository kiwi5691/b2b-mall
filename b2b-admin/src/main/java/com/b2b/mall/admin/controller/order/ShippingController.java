package com.b2b.mall.admin.controller.order;


import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.admin.service.ShippingService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.*;
import com.b2b.mall.db.model.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Log("打开发货管理")
    @RequestMapping("/user/shippingManage_{pageCurrent}_{pageSize}_{pageCount}")
    @RequiresPermissions(value ="shippingManage")
    public String orderManage(OrderShipping orderShipping, @PathVariable Integer pageCurrent,
                              @PathVariable Integer pageSize,
                              @PathVariable Integer pageCount,
                              Model model) {
        shippingService.orderManage(orderShipping,pageCurrent,pageSize,pageCount,model);
        return "order/shippingManage";
    }

    @Log("打开发货")
    @GetMapping("/user/sendOff")
    @RequiresPermissions(value ="shippingManage")
    public String sendOff(Model model, OrderShipping orderShipping) {
        shippingService.sendOffGet(model,orderShipping);
        return "order/sendOff";
    }

    @Log("提交发货")
    @PostMapping("/user/sendOff")
    @RequiresPermissions(value ="shippingManage")
    public String sendOffPost(Model model,OrderShipping orderShipping,String delieryCompes,String sendOffId){

        shippingService.sendOffPost(model, orderShipping, delieryCompes, sendOffId);
        return "redirect:shippingManage_0_0_0";
    }
}
