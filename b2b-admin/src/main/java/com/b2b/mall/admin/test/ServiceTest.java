package com.b2b.mall.admin.test;

import com.b2b.mall.db.mapper.LogMapper;
import com.b2b.mall.db.model.LogWithBlobs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 19:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartupApplication.class)
public class ServiceTest {

    @Autowired
     private LogMapper logMapper;

    List<LogWithBlobs> logWithBlobes;

    @Test
    public void TestfindOpLogs(){
        LogWithBlobs logWithBlobs = new LogWithBlobs();
        logWithBlobs.setStart(5);
        logWithBlobs.setEnd(5);

        logWithBlobes = logMapper.list(logWithBlobs);
        System.out.println(logWithBlobes.get(0).getMethod());

    }
}
