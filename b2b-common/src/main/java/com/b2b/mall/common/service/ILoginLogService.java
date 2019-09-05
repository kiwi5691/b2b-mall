package com.b2b.mall.common.service;


import com.b2b.mall.db.model.LoginLog;
import com.b2b.mall.db.model.User;
import org.springframework.ui.Model;


import java.util.List;
import java.util.Map;

/**
 * @author kiwi
 */
public interface ILoginLogService  {

    List<LoginLog> findLoginLogs(LoginLog loginLog, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);


    /**
     * 保存登录日志
     *
     * @param loginLog 登录日志
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 删除登录日志
     *
     * @param ids 日志 id集合
     */
    void deleteLoginLogs(String[] ids);

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
