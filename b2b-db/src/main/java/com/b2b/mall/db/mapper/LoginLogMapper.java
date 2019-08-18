package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.LoginLog;
import com.b2b.mall.db.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginLog record);

    int count();

    List<LoginLog> list(LoginLog loginLog);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);

    int delBeforeData(Date date);


    List<LoginLog> selBeforeData(Date date);

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount();

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Long findTodayIp();

    /**
     * 获取系统近七天来的访问记录
     *
     * @param user 用户
     * @return 系统近七天来的访问记录
     */
    List<Map<String, Object>> findLastSevenDaysVisitCount(User user);

}