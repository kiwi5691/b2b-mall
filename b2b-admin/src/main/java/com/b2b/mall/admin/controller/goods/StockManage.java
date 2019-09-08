package com.b2b.mall.admin.controller.goods;


import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.admin.service.Impl.StockServiceImpl;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.*;
import com.b2b.mall.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 库存管理
 * @author kiwi
 */
@Controller
public class StockManage {

   private final StockServiceImpl stockService;

   @Autowired
    public StockManage(StockServiceImpl stockService) {
        this.stockService = stockService;
    }

    @RequestMapping("/user/stockManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String stockManage(Item item, @PathVariable Integer pageCurrent,
                              @PathVariable Integer pageSize,
                              @PathVariable Integer pageCount,
                              Model model) {
        stockService.stockManage(item,pageCurrent,pageSize,pageCount,model);
        return "item/stockManage";
    }
}
