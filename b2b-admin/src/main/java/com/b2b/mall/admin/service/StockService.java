package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.Item;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author kiwi
 * @Date: 2019/6/26 12:52
 */
public interface StockService {
    void stockManage(Item item,  Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
}
