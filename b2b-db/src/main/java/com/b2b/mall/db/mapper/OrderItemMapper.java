package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    int deleteByPrimaryKey(String id);

    int insert(OrderItem record);

    OrderItem selectByPrimaryKey(String id);

    OrderItem selectByPrimaryOrderKey(String orderId);

    List<OrderItem> selectAll();

    int updateByPrimaryKey(OrderItem record);
}