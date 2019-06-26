package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.ItemCategoryService;
import com.b2b.mall.common.util.Constant;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.model.ItemCategory;
import com.b2b.mall.db.model.ResObject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 13:08
 */
@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    @Resource
    private ItemCategoryMapper itemCategoryMapper;

    @Override
    public void itemCategoryManage(ItemCategory itemCategory, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
        //判断
        if(pageSize == 0) {
            pageSize = 20;
        }
        if(pageCurrent == 0) {
            pageCurrent = 1;
        }
        int rows = itemCategoryMapper.count(itemCategory);
        if(pageCount == 0) {
            pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
        }
        itemCategory.setStart((pageCurrent - 1)*pageSize);
        itemCategory.setEnd(pageSize);
        List<ItemCategory> list = itemCategoryMapper.list(itemCategory);
        for (ItemCategory i : list){
            i.setCreatedStr(DateUtil.getDateStr(i.getCreated()));
        }
        model.addAttribute("list", list);
        String pageHTML = PageUtil.getPageContent("itemCategoryManage_{pageCurrent}_{pageSize}_{pageCount}?name="+itemCategory.getName(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("itemCategory", itemCategory);
    }

    @Override
    public void itemCategoryEditGet(Model model, ItemCategory itemCategory) {
        if(itemCategory.getId() != 0){
            ItemCategory itemCategory0 = itemCategoryMapper.findById(itemCategory);
            model.addAttribute("itemCategory",itemCategory0);
        }
    }

    @Override
    public String newsCategoryEditPost(Model model, ItemCategory itemCategory, MultipartFile[] imageFile, HttpSession httpSession) {
        //根据时间和随机数生成id
        Date date = new Date();
        Random random = new Random();
        // 获取3位随机数
        int rannum = (int) (random.nextDouble() * (999 - 100 + 1)) + 10;
        itemCategory.setCreated(date);
        itemCategory.setUpdated(date);
        List<ItemCategory> list = itemCategoryMapper.list1();
        String name = itemCategory.getName();
        for(ItemCategory i : list){
            if(i.getName().equals(name)){
                return "redirect:itemCategoryManage_0_0_0";
        }
        }
        if(itemCategory.getId() != 0){
            itemCategoryMapper.update(itemCategory);
        } else {
            itemCategory.setId(rannum);
            itemCategoryMapper.insert(itemCategory);
        }
        return "redirect:itemCategoryManage_0_0_0";
    }

    @Override
    public ResObject<Object> itemCategoryEditState(ItemCategory itemCategory) {
        itemCategoryMapper.delete(itemCategory);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }
}
