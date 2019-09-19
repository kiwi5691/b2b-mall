package com.b2b.mall.frontend.controller;

import com.b2b.dubbo.search.service.SearchItemService;
import com.b2b.mall.common.entity.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemController {

    @Reference(version = "${dubbo.version}",timeout = 300000)
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public Result impotItemIndex() {
        Result result = searchItemService.importItems();
        return result;
    }
}
