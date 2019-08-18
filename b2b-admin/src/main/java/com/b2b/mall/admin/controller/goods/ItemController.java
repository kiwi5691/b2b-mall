package com.b2b.mall.admin.controller.goods;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.admin.service.ItemService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.mapper.ItemMapper;
import com.b2b.mall.db.mapper.ReItemMapper;
import com.b2b.mall.db.model.*;

import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;


/**
 * 商品管理
 * @author kiwi
 */
@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }



    @Log("打开商品管理")
    @RequestMapping("/user/itemManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String itemManage(Item item, @PathVariable Integer pageCurrent,
                             @PathVariable Integer pageSize,
                             @PathVariable Integer pageCount,
                             Model model) {
        itemService.itemManage(item,pageCurrent,pageSize,pageCount,model);
        return "item/itemManage";
    }

    @Log("下载订单管理的execl")
    @RequestMapping("/user/download1")
    public void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        itemService.postItemExcel(request,response);
    }

    String imageName = null;

    @Log("打开修改商品")
    @GetMapping("/user/itemEdit")
    public String itemEditGet(Model model, Item item) {
        itemService.itemEditGet(model,item);
        return "item/itemEdit";
    }

    @Log("提交修改商品")
    @PostMapping("/user/itemEdit")
    public String itemEditPost(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file, Item item, HttpSession httpSession) {
        itemService.itemEditPost(model,request,file,item,httpSession);
        return "redirect:itemManage_0_0_0";
    }

    @Log("获取file信息")
    @GetMapping(value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile() {
        return itemService.getFile();
    }

    @Log("提交商品修改状态")
    @ResponseBody
    @PostMapping("/user/itemEditState")
    public ResObject<Object> itemEditState(Item item1) {
        return itemService.itemEditState(item1);
    }
}
