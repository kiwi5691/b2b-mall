package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.util.AddressUtil;
import com.b2b.mall.common.util.HttpContextUtil;
import com.b2b.mall.common.util.IPUtil;
import com.b2b.mall.db.mapper.LoginLogMapper;
import com.b2b.mall.db.model.LoginLog;
import com.b2b.mall.db.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//   @Override
//    public IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request) {
//        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
//
//        if (StringUtils.isNotBlank(loginLog.getUsername())) {
//            queryWrapper.lambda().eq(LoginLog::getUsername, loginLog.getUsername().toLowerCase());
//        }
//        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
//            queryWrapper.lambda()
//                    .ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
//                    .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
//        }
//
//        Page<LoginLog> page = new Page<>(request.getPageNum(), request.getPageSize());
//        SortUtil.handlePageSort(request, page, "loginTime", FebsConstant.ORDER_DESC, true);
//
//        return this.page(page, queryWrapper);
//    }

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
