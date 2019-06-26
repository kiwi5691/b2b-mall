package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.ItemCategory;
import com.b2b.mall.db.model.ResObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:51
 */
public interface ItemCategoryService {
    void itemCategoryManage(ItemCategory itemCategory, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
    void itemCategoryEditGet(Model model, ItemCategory itemCategory);
    String newsCategoryEditPost(Model model, ItemCategory itemCategory, @RequestParam MultipartFile[] imageFile, HttpSession httpSession);
    ResObject<Object> itemCategoryEditState(ItemCategory itemCategory);
}
