package com.b2b.mall.frontend.controller;

import com.b2b.mall.db.entity.ItemVo;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemDesc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther kiwi
 * @Date 2019/9/22 23:01
 */
@Controller
@RequestMapping("/info")
public class OrderInfoController {
    @RequestMapping("/order")
    public String searchtt( Model model) throws Exception {
        return "my-info";
    }

    @RequestMapping("/{orderId}.html")
    public String showItemInfo(@PathVariable Integer orderId, Model model) {
        //把数据传递给页面
//        model.addAttribute("item", item);
//        model.addAttribute("itemDesc", itemDesc);
        return "order-info";
    }
}
