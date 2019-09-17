package com.b2b.mall.frontend.controller;

import com.b2b.dubbo.frontend.service.ContentService;
import com.b2b.mall.db.model.Content;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Value("${CONTENT_BANNER_ID}")
    private Long CONTENT_BANNER_ID;

    @Reference
    private ContentService contentService;

    @RequestMapping({"/index", "/", "index.html"})
    public String showIndex(Model model) {
        List<Content> contentList = contentService.getContentList(CONTENT_BANNER_ID);
        model.addAttribute("ad1List", contentList);
        return "index";
    }

}
