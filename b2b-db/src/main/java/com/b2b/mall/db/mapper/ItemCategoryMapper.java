package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.ItemCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jiangyunxiong on 2018/3/10.
 */
@Mapper
public interface ItemCategoryMapper {

    ItemCategory findById(ItemCategory itemCategory);

    String findNameById(Integer id);

    List<ItemCategory> list(ItemCategory itemCategory);

    List<ItemCategory> list1();

    int count(ItemCategory itemCategory);

    int insert(ItemCategory itemCategory);

    int update(ItemCategory itemCategory);

    void delete(ItemCategory itemCategory);

    int updateStatus(ItemCategory itemCategory);

    List<ItemCategory> getItemCatByParentId(Integer parentId);

}
