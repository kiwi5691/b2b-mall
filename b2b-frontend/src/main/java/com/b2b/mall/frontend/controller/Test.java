package com.b2b.mall.frontend.controller;

import com.b2b.mall.db.mapper.LogMapper;
import com.b2b.mall.db.model.LogWithBlobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 20:25
 */
@RestController
public class Test {
    @Autowired
    private LogMapper logMapper;

    List<LogWithBlobs> logWithBlobes;

    @GetMapping("/test")
    public String TestfindOpLogs(LogWithBlobs logWithBlobs){

        logWithBlobs.setStart(5);//
        logWithBlobs.setEnd(5);//

        logWithBlobes = logMapper.list(logWithBlobs);//

        System.out.println(logWithBlobes.get(0).getIp());
        return "ficl";
    }

}
