package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.JobService;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.db.mapper.LoginLogMapper;
import com.b2b.mall.db.model.LoginLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @auther kiwi
 * @Date 2019/8/12 17:41
 */
@Service
public class JobServiceImpl implements JobService {

    @Resource
    private LoginLogMapper loginLogMapper;

    /**
     *@auther kiwi
     * 删除指定日期前的数据
     *@Data 2019/8/12
     @param  * @param
     *@reutn void
    */
    @Override
    public void delBeforeLog() {

    }

    @Override
    public void selBeforeLog() {
        List<LoginLog> loginLogs = loginLogMapper.selBeforeData(DateUtil.dateBefore7());
        Set<Date> loginTimes= loginLogs.stream().map(LoginLog::getLoginTime).collect(Collectors.toSet());

        loginTimes.stream().forEach(System.out::println);
    }
}