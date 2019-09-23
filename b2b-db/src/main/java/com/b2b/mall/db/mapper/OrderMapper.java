package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    Order selectByPrimaryKey(String orderId);

    List<Order> list(Order order);

    List<Order> getByUserId(@Param("id") Long id);

    List<Order> listRefund(Order order);

    List<Order> selectAll();

    Integer selectCurOrderNum();
    Integer selectLastOrderNum();

    Long selectCurPayment();
    Long selectLastPayment();

    Integer selectCurRefundOrder();
    Integer selectLastRefundOrder();

    Integer selectDayOrderSum(Order order);
    Integer selectDayOrderNum(Order order);

    Integer updateByPrimaryKey(Order record);

    Integer updateByShippingState(Order record);

}