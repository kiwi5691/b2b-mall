package com.b2b.mall.admin.controller.order;


import com.b2b.mall.admin.service.OrderService;
import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.*;
import com.b2b.mall.db.model.*;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 订单管理
 * @author kiwi
 */
@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Log("打开订单管理")
    @RequestMapping("/user/orderManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String orderManage(Order order, @PathVariable Integer pageCurrent,
                              @PathVariable Integer pageSize,
                              @PathVariable Integer pageCount,
                              Model model) {
        orderService.orderManage(order,pageCurrent,pageSize,pageCount,model);
        return "order/orderManage";
    }

    @RequestMapping("/user/download2")
    public void downstudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
       orderService.downstudents(request,response);
    }

    @Log("打开订单详细")
    @GetMapping("/user/orderDetails")
    public String orderDetailsGet(Model model, Order order) {
        orderService.orderDetailsGet(model,order);
        return "order/orderDetails";
    }

    @Log("提交订单详细")
    @PostMapping("/user/orderDetails")
    public String orderDetailsPost(Model model, @RequestParam MultipartFile[] imageFile, HttpSession httpSession) {
        return null;
    }


    /**
     * 退款管理
     *
     * @param order
     * @param pageCurrent
     * @param pageSize
     * @param pageCount
     * @param model
     * @return
     */
    @RequestMapping("/user/orderRefund_{pageCurrent}_{pageSize}_{pageCount}")
    public String refundManage(Order order, @PathVariable Integer pageCurrent,
                               @PathVariable Integer pageSize,
                               @PathVariable Integer pageCount,
                               Model model) {
        orderService.refundManage(order,pageCurrent,pageSize,pageCount,model);
        return "order/orderRefund";
    }

    /**
     * 审批
     *
     * @param model
     * @param order
     * @return
     */
    @Log("打开退款审核")
    @GetMapping("/user/orderCheck")
    public String orderCheckGet(Model model, Order order) {
        orderService.orderCheckGet(model,order);
        return "order/orderCheck";
    }

    @Log("提交退款审核")
    @PostMapping("/user/orderCheck")
    public String orderCheckPost(Model model, @RequestParam MultipartFile[] imageFile, Order order, HttpSession httpSession) {

        orderService.orderCheckPost(model,imageFile,order,httpSession);
        return "redirect:orderRefund_0_0_0";
    }

}
