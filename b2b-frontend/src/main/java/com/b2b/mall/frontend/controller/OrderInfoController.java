package com.b2b.mall.frontend.controller;

import com.b2b.dubbo.order.service.OrderInfoService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.db.entity.ItemVo;
import com.b2b.mall.db.entity.MallUserDTO;
import com.b2b.mall.db.model.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/22 23:01
 */
@Controller
@RequestMapping("/info")
public class OrderInfoController {

    @Reference(version = "${dubbo.version}")
    private OrderInfoService orderInfoService;


    @RequestMapping("/order")
    public String searchtt( HttpServletRequest request) throws Exception {
        TbUser user = (TbUser) request.getAttribute("user");
        System.out.println(user.getId());
        List<Order> orderList = orderInfoService.getOrderById((long) 37);

        orderList.forEach(order -> {
            order.setDateStr1(DateUtil.getDateStr(order.getCreateTime()));
            order.setDateStr1(BaseHTMLStringCase.isSent(order.getShippingCode()));
        });

        MallUserDTO mallUserDTO = new MallUserDTO(user.getId(),user.getUsername(),user.getPassword(),user.getPhone(),user.getEmail(),DateUtil.getDateStr(user.getCreated()),DateUtil.getDateStr(user.getUpdated()));

        request.setAttribute("orderList", orderList);
        request.setAttribute("mallUserDTO", mallUserDTO);

        return "my-info";
    }

    @RequestMapping("/{orderId}.html")
    public String showItemInfo(@PathVariable Integer orderId, Model model) {


        OrderShipping orderShipping = orderInfoService.getAddressByOrderId(String.valueOf(orderId));
        List<OrderItem> orderItemList =orderInfoService.getOrderItemByOrderId(String.valueOf(orderId));

        model.addAttribute("orderShipping", orderShipping);
        model.addAttribute("orderItemList", orderItemList);
        return "order-info";
    }
}
