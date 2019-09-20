package com.b2b.mall.frontend.controller;

import com.b2b.dubbo.search.service.SearchService;
import com.b2b.mall.db.entity.SearchResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Reference(version = "${dubbo.version}")
    private SearchService searchService;
    @Value("${PAGE_ROWS}")
    private Integer PAGE_ROWS;

    @RequestMapping("/searcht.html")
    public String searcht( Model model) throws Exception {
        return "shop-search";
    }
    @RequestMapping("/cartt.html")
    public String searchtt( Model model) throws Exception {
        return "shop-cart";
    }
    @RequestMapping("/checkt.html")
    public String searchttt( Model model) throws Exception {
        return "shop-checkout";
    }
    @RequestMapping("/indext.html")
    public String searchtttt( Model model) throws Exception {
        return "shop-index";
    }

    @RequestMapping("/sing.html")
    public String searchttttt( Model model) throws Exception {
        return "single-product";
    }
        @RequestMapping("/search.html")
    public String search(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        //调用Service查询商品信息
        SearchResult result = searchService.search(keyword, page, PAGE_ROWS);
        //把结果传递给jsp页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("recourdCount", result.getRecourdCount());
        model.addAttribute("page", page);
        model.addAttribute("itemList", result.getItemList());
        //返回逻辑视图
        return "search";
    }
}
