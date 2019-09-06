package com.b2b.mall.admin.controller;

import com.b2b.mall.db.model.LogWithBlobs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 20:25
 */
@Slf4j
@RestController
public class Test {


    List<LogWithBlobs> logWithBlobes;

    @GetMapping("/test")
    public String TestfindOpLogs(){

//        LogWithBlobs logWithBlobs = new LogWithBlobs();
//        logWithBlobs.setStart(5);
//        logWithBlobs.setEnd(5);
//
//        logWithBlobes = logMapper.list(logWithBlobs);
//
//
//        logWithBlobes.forEach(l->System.out.println(l.toString()));
        return "ficl";
    }

}
