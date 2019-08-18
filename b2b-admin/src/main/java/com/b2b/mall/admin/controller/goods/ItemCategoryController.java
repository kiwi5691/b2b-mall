package com.b2b.mall.admin.controller.goods;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.admin.service.Impl.ItemCategoryServiceImpl;
import com.b2b.mall.admin.service.ItemCategoryService;
import com.b2b.mall.common.util.Constant;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 商品分类管理
 */
@Controller
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @Autowired
    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @Log("打开商品分类")
    @RequestMapping("/user/itemCategoryManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String itemCategoryManage(ItemCategory itemCategory,
                                     @PathVariable Integer pageCurrent,
                                     @PathVariable Integer pageSize,
                                     @PathVariable Integer pageCount,
                                     Model model){
        itemCategoryService.itemCategoryManage(itemCategory,pageCurrent,pageSize,pageCount,model);
        return "item/itemCategoryManage";
    }

    @Log("打开商品分类修改")
    @GetMapping("/user/itemCategoryEdit")
    public String itemCategoryEditGet(Model model, ItemCategory itemCategory) {
        itemCategoryService.itemCategoryEditGet(model,itemCategory);
        return "item/itemCategoryEdit";
    }

    @Log("提交商品分类修改")
    @PostMapping("/user/itemCategoryEdit")
    public String newsCategoryEditPost(Model model, ItemCategory itemCategory, @RequestParam MultipartFile[] imageFile, HttpSession httpSession) {
        return itemCategoryService.newsCategoryEditPost(model,itemCategory,imageFile,httpSession);
    }

    @Log("提交商品分类状态")
    @ResponseBody
    @PostMapping("/user/itemCategoryEditState")
    public ResObject<Object> itemCategoryEditState(ItemCategory itemCategory){
        return itemCategoryService.itemCategoryEditState(itemCategory);
    }
}
