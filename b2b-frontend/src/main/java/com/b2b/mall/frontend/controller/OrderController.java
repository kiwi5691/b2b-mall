package com.b2b.mall.frontend.controller;


import com.b2b.dubbo.cart.service.CartService;
import com.b2b.dubbo.order.service.OrderService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.OrderInfo;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.TbUser;
import org.joda.time.DateTime;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理Controller
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference(version = "${dubbo.version}")
    private CartService cartService;
    @Reference(version = "${dubbo.version}")
    private OrderService orderService;

    /**
     * 跳转订单确认页面
     * @param request
     * @return
     */
    @RequestMapping("/order-cart.html")
    public String showOrderCart(HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute("user");
        List<Item> cartList = cartService.getCartList(user.getId());
        cartList.stream().forEach(cartList1->{
            System.out.println(cartList1.getId());
            System.out.println(cartList1.getPrice());
            System.out.println(cartList1.getTitle());
            System.out.println(cartList1.getCategoryName());
            System.out.println(cartList1.getNum());
        });
        request.setAttribute("cartList", cartList);
        return "shop-checkout";
//        return "order-cart";
    }

    @PostMapping("/create.html")
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
        // 1、接收表单提交的数据OrderInfo。
        // 2、补全用户信息。
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        // 3、调用Service创建订单。
        Result result = orderService.createOrder(orderInfo);
        if (result.getStatus() == 200) {
            // 清空购物车
            cartService.clearCartList(user.getId());
        }
        //取订单号
        String orderId = result.getData().toString();
        // a)需要Service返回订单号
        request.setAttribute("orderId", orderId);
        request.setAttribute("payment", orderInfo.getPayment());
        // b)当前日期加三天。
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));
        // 4、返回逻辑视图展示成功页面
        return "success";
    }

}
