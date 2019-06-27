package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.ReItem;
import com.b2b.mall.db.model.ResObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author kiwi
 * @Date: 2019/6/26 12:52
 */
public interface ReltemService {
    void itemManage(ReItem reItem,Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
    ResObject<Object> reItemEditState(ReItem reItem);
    ResObject<Object> deleteItemEditState(ReItem reItem);
}
