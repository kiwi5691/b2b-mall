package com.b2b.mall.admin.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.b2b.mall.admin.annotation.Log;
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

    @Log("打开物流公司")
    @RequestMapping("user/deliveryManage")
    public String deliveryManage(Delivery delivery, Model model){
       deliveryService.deliveryManage(delivery,model);
        return "others/deliveryManage";
    }

    @Log("搜索快递公司")
    @GetMapping("user/search")
    public String searchGet(Model model, Delivery delivery){
        deliveryService.searchGet(model,delivery);
        return "others/search";
    }

    @Log("提交搜索快递公司")
    @PostMapping("user/search")
    public String searchPost(Model model, Delivery delivery){
        deliveryService.searchPost(model,delivery);
        return "others/search";
    }

    public JSONArray getExpress100(String deliveryCode, String expressNo) {
        return deliveryService.getExpress100(deliveryCode,expressNo);
    }

    @Log("编辑快递")
    @GetMapping("user/deliveryEdit")
    public String deliveryEditGet(Model model, Delivery delivery){
        return "others/deliveryEdit";
    }

    @Log("提交编辑快递")
    @PostMapping("user/deliveryEdit")
    public String deliveryEditPost(Model model, Delivery delivery){
        deliveryService.deliveryEditPost(model,delivery);
        return "redirect:deliveryManage";
    }


    @Log("删除快递")
    @RequestMapping("user/deliveryDeleteState")
    public String deliveryDeleteStatePost(Model model, Delivery delivery){
        deliveryService.deliveryDeleteStatePost(model,delivery);
        return "redirect:deliveryManage";
    }

}
