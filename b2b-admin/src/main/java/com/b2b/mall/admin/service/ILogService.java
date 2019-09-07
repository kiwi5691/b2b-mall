package com.b2b.mall.admin.service;


import com.b2b.mall.db.model.LogWithBlobs;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kiwi
 */
public interface ILogService {


    /**
     * 异步保存操作日志
     *
     * @param point 切点
     * @param log   日志
     * @throws JsonProcessingException 异常
     */
    @Async
    void saveLog(ProceedingJoinPoint point, LogWithBlobs log) throws JsonProcessingException;
}
