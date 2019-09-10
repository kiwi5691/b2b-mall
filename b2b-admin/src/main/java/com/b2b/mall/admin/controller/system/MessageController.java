package com.b2b.mall.admin.controller.system;

import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.service.MessageService;
import com.b2b.mall.db.entity.MessageVo;
import com.b2b.mall.db.mapper.sysMessageLogMapper;
import com.b2b.mall.db.mapper.sysMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @auther kiwi
 * @Date 2019/9/10 14:07
 */
@Controller
public class MessageController {


    @Autowired
    private MessageService messageService;

    @GetMapping("/user/loginLog_{pageCurrent}_{pageSize}_{pageCount}")
    public String getMessage(MessageVo messageVo, @PathVariable Integer pageCurrent,
                             @PathVariable Integer pageSize,
                             @PathVariable Integer pageCount,
                             Model model){
        messageService.findOpLogs(messageVo,pageCurrent,pageSize,pageCount,model);
        return "log/messageQueue";
    }
}
