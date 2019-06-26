package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.StockService;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.mapper.ItemMapper;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kiwi
 * @Date: 2019/6/26 13:09
 */
@Service
public class StockServiceImpl implements StockService {
    @Resource
    private ItemMapper itemMapper;

    @Resource
    private ItemCategoryMapper itemCategoryMapper;

    @Override
    public void stockManage(Item item, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {

        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }
        int rows = itemMapper.count(item);
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        List<Item> itemList = itemMapper.listS(item);
        for (Item i : itemList){
            i.setUpdatedStr(DateUtil.getDateStr(i.getUpdated()));
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setStart(0);
        itemCategory.setEnd(Integer.MAX_VALUE);
        List<ItemCategory> itemCategoryList = itemCategoryMapper.list(itemCategory);
        Integer minNum = item.getMinNum();
        Integer maxNum = item.getMaxNum();
        model.addAttribute("itemCategoryList", itemCategoryList);
        model.addAttribute("itemList", itemList);
        String pageHTML = PageUtil.getPageContent("stockManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getTitle() + "&cid=" + item.getCid() + "&minNum" + minNum + "&maxNum" + maxNum, pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
    }
}
