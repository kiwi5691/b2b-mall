package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.DashboardService;
import com.b2b.mall.common.redis.DashboardKey;
import com.b2b.mall.common.redis.RedisService;
import com.b2b.mall.common.util.RunnableThreadWebCount;
import com.b2b.mall.db.mapper.OrderMapper;
import com.b2b.mall.db.model.Order;
import com.b2b.mall.db.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *@author kiwi
 *@Data 2019/6/26
*/
@Service
public class DashboardServiceImpl implements DashboardService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisService redisService;

    @Override
    public void dashboardInit(Model model, Stats stats) {

        Long mIncome, lastIncome;
        Integer curOrderNum, preOrderNum, curRefundOrder, lastRefundOrder, orderNum, orderSum;

        //全部加缓存
        mIncome = redisService.get(DashboardKey.board, "mIncome", Long.class);
        if (mIncome == null) {
            mIncome = orderMapper.selectCurPayment();
            mIncome = mIncome == null ? 0L : mIncome;
            redisService.set(DashboardKey.board, "mIncome", mIncome);
        }

        lastIncome = redisService.get(DashboardKey.board, "lastIncome", Long.class);
        if (lastIncome == null) {
            lastIncome = orderMapper.selectLastPayment();
            lastIncome = lastIncome == null ? 0L : lastIncome;
            redisService.set(DashboardKey.board, "lastIncome", lastIncome);
        }

        curOrderNum = redisService.get(DashboardKey.board, "curOrderNum", Integer.class);
        if (curOrderNum == null) {
            curOrderNum = orderMapper.selectCurOrderNum();
            curOrderNum = curOrderNum == null ? 0 : curOrderNum;
            redisService.set(DashboardKey.board, "curOrderNum", curOrderNum);
        }

        preOrderNum = redisService.get(DashboardKey.board, "preOrderNum", Integer.class);
        if (preOrderNum == null) {
            preOrderNum = orderMapper.selectLastOrderNum();
            preOrderNum = preOrderNum == null ? 0 : preOrderNum;
            redisService.set(DashboardKey.board, "preOrderNum", preOrderNum);
        }

        curRefundOrder = redisService.get(DashboardKey.board, "preOrderNum", Integer.class);
        if (curRefundOrder == null) {
            curRefundOrder = orderMapper.selectCurRefundOrder();
            curRefundOrder = curRefundOrder == null ? 0 : curRefundOrder;
            redisService.set(DashboardKey.board, "curRefundOrder", curRefundOrder);
        }

        lastRefundOrder = redisService.get(DashboardKey.board, "lastRefundOrder", Integer.class);
        if (lastRefundOrder == null) {
            lastRefundOrder = orderMapper.selectLastRefundOrder();
            lastRefundOrder = lastRefundOrder == null ? 0 : lastRefundOrder;
            redisService.set(DashboardKey.board, "lastRefundOrder", lastRefundOrder);
        }

        int count = RunnableThreadWebCount.addCount("111");
        //访问量
        stats.setPv(count);
        //月订单数环比
        stats.setOrderNumPer(getPer(curOrderNum, preOrderNum));
        //月订单数
        stats.setmOrderNum(orderMapper.selectCurOrderNum());
        //月收入
        stats.setmIncome(mIncome);
        //月收入环比
        stats.setIncomePer(getPer(mIncome, lastIncome));
        stats.setmOrderRefund(orderMapper.selectCurRefundOrder());
        stats.setmOrderRefundPer(getPer(curRefundOrder, lastRefundOrder));

        model.addAttribute("dashboard", stats);

        List<Integer> data2 = new ArrayList<>();
        List<Integer> data3 = new ArrayList<>();

        Date now = new Date();
        //获取三十天前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //最后一个数字30可改，30天的意思
        calendar.add(Calendar.DATE, -31);

        Date temp = new Date();
        Order order = new Order();
        for (int i = 0; i < 31; i++) {
            calendar.add(Calendar.DATE, 1);
            temp = calendar.getTime();
            order.setCreateTime(temp);
            //每天的订单数
            orderNum = redisService.get(DashboardKey.board, "orderNum", Integer.class);
            if (orderNum == null) {
                orderNum = orderMapper.selectDayOrderNum(order);
                orderNum = orderNum == null ? 0 : orderNum;
                redisService.set(DashboardKey.board, "orderNum", orderNum);
            }

            //每天的收入
            orderSum = redisService.get(DashboardKey.board, "orderSum", Integer.class);
            if (orderSum == null) {
                orderSum = orderMapper.selectDayOrderSum(order);
                orderSum = orderSum == null ? 0 : orderSum;
                redisService.set(DashboardKey.board, "orderSum", orderSum);
            }
            data2.add(orderNum);
            data3.add(orderSum);
        }

        model.addAttribute("data2", data2);
        model.addAttribute("data3", data3);

    }

    public String getPer(long a, long b) {
        StringBuilder orderNumPer = new StringBuilder();
        double differ = a - b;
        double d = differ / a;
        String s = String.format("%.2f", d);
        orderNumPer.append(s).append("%");
        return orderNumPer.toString();
    }
}
