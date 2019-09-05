package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.OpLogService;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.db.mapper.LogMapper;
import com.b2b.mall.db.model.LogWithBlobs;
import com.b2b.mall.db.model.LoginLog;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 16:38
 */
@Service
public class OpLogServiceImpl implements OpLogService {

    @Resource
    private LogMapper logMapper;

    List<LogWithBlobs> logWithBlobes;

    @Override
    public List<LogWithBlobs> findOpLogs(LogWithBlobs logWithBlobs, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }

        int rows = logMapper.count();
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        logWithBlobs.setStart((pageCurrent - 1) * pageSize);
        logWithBlobs.setEnd(pageSize);

        logWithBlobes = logMapper.list(logWithBlobs);
     //   System.out.println(logWithBlobes.get(0).getMethod());

     //   logWithBlobes.forEach(l -> l.setTimeStr(DateUtil.preciseDate(l.getCreateTime())));
        logWithBlobes.stream().filter(logWithBlobs1 -> {
            if(logWithBlobs1.getUsername()==null) {
                logWithBlobs1.setUsername(" 无");
            }
            else if (logWithBlobs1.getMethod()==null) {
                logWithBlobs1.getOperation("无")
            }
        });

        model.addAttribute("logWithBlobes", logWithBlobes);
        return logWithBlobes;
    }
}
