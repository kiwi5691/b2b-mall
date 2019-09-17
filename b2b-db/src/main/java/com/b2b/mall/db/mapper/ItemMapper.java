package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    Item findById(Item item);

    void delete(Item item);

    List<Item> list(Item item);

    List<Item> listS(Item item);

    List<Item> getItemList();

    int count(Item item);

    int insert(Item item);

    int update(Item item);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll();
}
