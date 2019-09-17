package com.b2b.dubbo.sso.service.Impl;

import com.b2b.dubbo.sso.service.ItemCategoryService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.db.entity.BaseUIDTO;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.model.ItemCategory;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Override
    public List<BaseUIDTO> getCatList(Integer parentId) {
        // 1、根据parentId查询节点列表
        //设置查询条件
        List<ItemCategory> list = itemCategoryMapper.getItemCatByParentId(parentId);
        // 2、转换成EasyUITreeNode列表。
        List<BaseUIDTO> resultList = new ArrayList<>();
        for (ItemCategory tbItemCat : list) {
            BaseUIDTO baseUIDTO = new BaseUIDTO();
            baseUIDTO.setId(tbItemCat.getId());
            baseUIDTO.setText(tbItemCat.getName());
            baseUIDTO.setState(BaseHTMLStringCase.isParent(tbItemCat.getIsParent())? "closed" : "open");
            //添加到列表
            resultList.add(baseUIDTO);
        }
        // 3、返回。
        return resultList;
    }


}
