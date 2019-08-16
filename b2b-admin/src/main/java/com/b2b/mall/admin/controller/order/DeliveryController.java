package com.b2b.mall.admin.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.b2b.mall.admin.service.DeliveryService;
import com.b2b.mall.admin.service.Impl.DeliveryServiceImpl;
import com.b2b.mall.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 回收管理
 * @author kiwi
 */
@Controller
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RequestMapping("user/deliveryManage")
    public String deliveryManage(Delivery delivery, Model model){
       deliveryService.deliveryManage(delivery,model);
        return "others/deliveryManage";
    }

    @GetMapping("user/search")
    public String searchGet(Model model, Delivery delivery){
        deliveryService.searchGet(model,delivery);
        return "others/search";
    }

    @PostMapping("user/search")
    public String searchPost(Model model, Delivery delivery){
        deliveryService.searchPost(model,delivery);
        return "others/search";
    }

    public JSONArray getExpress100(String deliveryCode, String expressNo) {
        return deliveryService.getExpress100(deliveryCode,expressNo);
    }

    @GetMapping("user/deliveryEdit")
    public String deliveryEditGet(Model model, Delivery delivery){
        return "others/deliveryEdit";
    }

    @PostMapping("user/deliveryEdit")
    public String deliveryEditPost(Model model, Delivery delivery){
        deliveryService.deliveryEditPost(model,delivery);
        return "redirect:deliveryManage";
    }


    @RequestMapping("user/deliveryDeleteState")
    public String deliveryDeleteStatePost(Model model, Delivery delivery){
        deliveryService.deliveryDeleteStatePost(model,delivery);
        return "redirect:deliveryManage";
    }

}
