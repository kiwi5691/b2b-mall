package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.ReltemService;
import com.b2b.mall.common.util.Constant;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.ItemMapper;
import com.b2b.mall.db.mapper.ReItemMapper;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ReItem;
import com.b2b.mall.db.model.ResObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 13:09
 */
@Service
public class ReltemServiceImpl implements ReltemService {
    @Resource
    private ReItemMapper reItemMapper;

    @Resource
    private ItemMapper itemMapper;

    @Override
    public void itemManage(ReItem reItem, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {

        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }
        int rows = reItemMapper.selectAll().size();
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        reItem.setStart((pageCurrent - 1) * pageSize);
        reItem.setEnd(pageSize);
        List<ReItem> reItemList = reItemMapper.selectAll();
        for (ReItem r : reItemList) {
            r.setRecoveredStr(DateUtil.getDateStr(r.getRecovered()));
        }
        model.addAttribute("reItemList", reItemList);
        String pageHTML = PageUtil.getPageContent("itemManage_{pageCurrent}_{pageSize}_{pageCount}?", pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("ReItem", reItem);
    }

    @Override
    public ResObject<Object> reItemEditState(ReItem reItem) {
        ReItem reItem1 = reItemMapper.selectByPrimaryKey(reItem.getId());
        Item item = new Item();
        item.setId(reItem1.getId());
        item.setBarcode(reItem1.getBarcode());
        item.setCid(reItem1.getCid());
        item.setImage(reItem1.getImage());
        item.setPrice(reItem1.getPrice());
        item.setNum(reItem1.getNum());
        item.setSellPoint(reItem1.getSellPoint());
        item.setStatus(reItem1.getStatus());
        item.setTitle(reItem1.getTitle());
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.insert(item);
        reItemMapper.deleteByPrimaryKey(reItem.getId());
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }

    @Override
    public ResObject<Object> deleteItemEditState(ReItem reItem) {
        reItemMapper.deleteByPrimaryKey(reItem.getId());
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }
}
