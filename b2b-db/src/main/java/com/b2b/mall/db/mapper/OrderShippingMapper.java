package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.OrderShipping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface OrderShippingMapper {

    int count();

    int deleteByPrimaryKey(String orderId);

    int insert(OrderShipping record);

    OrderShipping selectByPrimaryKey(String orderId);

    List<OrderShipping> selectAll();

    int updateByPrimaryKey(OrderShipping record);
}