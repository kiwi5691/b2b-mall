package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.entity.dto.LoginLogDto;
import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.LoginLogMapper;
import com.b2b.mall.db.model.LoginLog;
import com.b2b.mall.db.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kiwi
 */
@Slf4j
@Service
public class LoginLogServiceImpl implements ILoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    List<LoginLog> loginLogs;

    @Override
    public List<LoginLog> findLoginLogs(LoginLog loginLog, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }

        int rows = loginLogMapper.count();
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        loginLog.setStart((pageCurrent - 1) * pageSize);
        loginLog.setEnd(pageSize);

        loginLogs = loginLogMapper.list(loginLog);

        loginLogs.forEach(l -> l.setTimeStr(DateUtil.preciseDate(l.getLoginTime())));

        String pageHTML = PageUtil.getPageContent("loginLog_{pageCurrent}_{pageSize}_{pageCount}?username=" + loginLog.getUsername() + "&id" + loginLog.getId() + "&timeStr" + loginLog.getTimeStr() + "&location" + loginLog.getLocation() + "&ip" + loginLog.getIp()+"&system"+loginLog.getSystem()+"&browser"+loginLog.getBrowser(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("loginLogs", loginLogs);
        return loginLogs;

    }

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        loginLogMapper.insert(loginLog);
    }



    @Override
    @Transactional
    public void deleteLoginLogs(String[] ids) {
//        List<String> list = Arrays.asList(ids);
//        loginLogMapper.deleteByPrimaryKey(loginLog);
    }

    @Override
    public Long findTotalVisitCount() {
        return loginLogMapper.findTotalVisitCount();
    }

    @Override
    public Long findTodayVisitCount() {
        return loginLogMapper.findTodayVisitCount();
    }

    @Override
    public Long findTodayIp() {
        return loginLogMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
        return loginLogMapper.findLastSevenDaysVisitCount(user);
    }
}
