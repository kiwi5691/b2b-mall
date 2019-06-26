package com.b2b.mall.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.b2b.mall.db.model.Delivery;
import org.springframework.ui.Model;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:51
 */
public interface DeliveryService {
    void deliveryManage(Delivery delivery, Model model);
    void  searchGet(Model model, Delivery delivery);
    void searchPost(Model model, Delivery delivery);
    JSONArray getExpress100(String deliveryCode, String expressNo);
    void deliveryEditPost(Model model, Delivery delivery);
    void deliveryDeleteStatePost(Model model, Delivery delivery);
}
