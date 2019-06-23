package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Delivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    int deleteByPrimaryKey(Integer id);
    int deleteByDeliveryName(String deliveryName);

    int insert(Delivery record);

    Delivery selectByPrimaryKey(Integer id);

    List<Delivery> selectAll();

    int updateByPrimaryKey(Delivery record);

    Delivery selectByName(String name);
}