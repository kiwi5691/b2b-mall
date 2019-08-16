package com.b2b.mall.admin.controller.goods;


import com.b2b.mall.admin.service.ReltemService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.*;
import com.b2b.mall.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 回收管理
 */
@Controller
public class ReItemController {

    private final ReltemService reltemService;

    @Autowired
    public ReItemController(ReltemService reltemService) {
        this.reltemService = reltemService;
    }

    @RequestMapping("/user/recoverManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String itemManage(ReItem reItem, @PathVariable Integer pageCurrent,
                             @PathVariable Integer pageSize,
                             @PathVariable Integer pageCount,
                             Model model) {
        reltemService.itemManage(reItem,pageCurrent,pageSize,pageCount,model);
        return "item/recoverManage";
    }

    @ResponseBody
    @PostMapping("/user/reItemEditState")
    public ResObject<Object> reItemEditState(ReItem reItem) {
        return reltemService.reItemEditState(reItem);
    }

    @ResponseBody
    @PostMapping("/user/deleteItemEditState")
    public ResObject<Object> deleteItemEditState(ReItem reItem) {
        return reltemService.deleteItemEditState(reItem);
    }

}
