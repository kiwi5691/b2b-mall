package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:52
 */
public interface OrderService {
    void orderManage(Order order,Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
    void downstudents(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void orderDetailsGet(Model model, Order order);
    void orderDetailsPost(Model model, @RequestParam MultipartFile[] imageFile, HttpSession httpSession);
    void refundManage(Order order, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
    void orderCheckGet(Model model, Order order);
    void orderCheckPost(Model model, @RequestParam MultipartFile[] imageFile, Order order, HttpSession httpSession);
}

