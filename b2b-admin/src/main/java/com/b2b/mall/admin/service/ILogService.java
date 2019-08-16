package com.b2b.mall.admin.service;


import com.b2b.mall.db.model.LogWithBlobs;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author kiwi
 */
public interface ILogService {

//    /**
//     * 查询操作日志分页
//     *
//     * @param log     日志
//     * @param request QueryRequest
//     * @return IPage<Log>
//     */
//    IPage<Log> findLogs(Log log, QueryRequest request);

    /**
     * 删除操作日志
     *
     * @param logIds 日志 ID集合
     */
    void deleteLogs(String[] logIds);

    /**
     * 异步保存操作日志
     *
     * @param point 切点
     * @param log   日志
     * @throws JsonProcessingException 异常
     */
    @Async("febsAsyncThreadPool")
    void saveLog(ProceedingJoinPoint point, LogWithBlobs log) throws JsonProcessingException;
}
