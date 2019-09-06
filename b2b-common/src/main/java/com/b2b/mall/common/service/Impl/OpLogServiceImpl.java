package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.OpLogService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.mapper.LogMapper;
import com.b2b.mall.db.model.LogWithBlobs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 16:38
 */
@Slf4j
@Service
public class OpLogServiceImpl implements OpLogService {

    @Resource
    private LogMapper logMapper;

    List<LogWithBlobs> logVOS ;

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

        logVOS = logMapper.list(logWithBlobs);

        logVOS.forEach(logWithBlobs1 -> {
            try {
                BaseHTMLStringCase.isNull(logWithBlobs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        logVOS.forEach(l -> l.setTimeStr(DateUtil.preciseDate(l.getCreateTime())));

        String pageHTML = PageUtil.getPageContent("opLog_{pageCurrent}_{pageSize}_{pageCount}?id=" + logWithBlobs.getId() + "&username" + logWithBlobs.getUsername() + "&operation" + logWithBlobs.getOperation() + "&time" + logWithBlobs.getTime() + "&method" + logWithBlobs.getMethod()+"&params"+logWithBlobs.getParams()+"%ip"+logWithBlobs.getIp()+"&timeStr"+logWithBlobs.getTimeStr()+"$location"+logWithBlobs.getLocation(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("logVOS", logVOS);
        return logVOS;
    }
}
