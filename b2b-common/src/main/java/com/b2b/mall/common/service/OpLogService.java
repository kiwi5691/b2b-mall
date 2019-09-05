package com.b2b.mall.common.service;

import com.b2b.mall.db.model.LogWithBlobs;
import com.b2b.mall.db.model.LoginLog;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 16:35
 */
public interface OpLogService {

    List<LogWithBlobs> findOpLogs(LogWithBlobs logWithBlobs, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);

}
