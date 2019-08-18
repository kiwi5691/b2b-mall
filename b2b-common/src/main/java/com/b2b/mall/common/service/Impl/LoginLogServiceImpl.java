package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.entity.dto.LoginLogDto;
import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.LoginLogMapper;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemCategory;
import com.b2b.mall.db.model.LoginLog;
import com.b2b.mall.db.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kiwi
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    List<LoginLog> loginLogs;
    List<LoginLogDto> loginLogDtos;

    @Override
    public void findLoginLogs(LoginLog loginLog, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
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

        for (LoginLog loginLog1 : loginLogs) {
            LoginLogDto loginLogDto = new LoginLogDto();
            loginLogDto.setBrowser(loginLog1.getBrowser());
            loginLogDto.setId(loginLog1.getId());
            loginLogDto.setIp(loginLog1.getIp());
            loginLogDto.setLocation(loginLog1.getLocation());
            loginLogDto.setSystem(loginLog1.getSystem());
            loginLogDto.setUsername(loginLog1.getUsername());
            loginLogDto.setLoginTime(DateUtil.preciseDate(loginLog1.getLoginTime()));
            loginLogDtos.add(loginLogDto);
        }

//        model.addAttribute("loginLogDtos", loginLogDtos);
//        LoginLogDto loginLogDto;
//        String pageHTML = PageUtil.getPageContent("itemManage_{pageCurrent}_{pageSize}_{pageCount}?title=" +  + "&userName=" + loginLogDto.getUsername() + "&minPrice" + minPrice + "&maxPrice" + maxPrice, pageCurrent, pageSize, pageCount);
//        model.addAttribute("pageHTML", pageHTML);
//        model.addAttribute("item", item);
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
