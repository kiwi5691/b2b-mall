package com.b2b.mall.admin.controller;

import com.b2b.mall.common.jms.ActiveMQService;
import com.b2b.mall.common.util.EmailTemplate;
import com.b2b.mall.common.util.EmailUtil;
import com.b2b.mall.common.util.UUIDUtils;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.mapper.UserRoleKeyMapper;
import com.b2b.mall.db.model.LogWithBlobs;
import com.b2b.mall.db.model.User;
import com.b2b.mall.db.model.UserRoleKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 20:25
 */
@Slf4j
@RestController
public class Test {



    @Autowired
    private ActiveMQService activeMQService;

    @RequestMapping("/test")
    public void TestfindOpLogs(User user) throws IOException, InterruptedException {

//        activeMQService.sendEmail("805344479@qq.com","fuck",EmailTemplate.registerTemplate("11","123"));
        while(true){
            Integer l =0;
            Thread.sleep(1000);
            activeMQService.textQueue("fuckfuck"+l);
            l++;
        }
    }

    //TODO 商城前台非付款的 不能转移到发货管理，付款成功后生成order_shiping表
    //TODO  还有退款的。
    //TODO 修改订单id。改成bigint，然后把所有的构造改成long
    //TODO 前台拍下。生成order。order_item表

}
