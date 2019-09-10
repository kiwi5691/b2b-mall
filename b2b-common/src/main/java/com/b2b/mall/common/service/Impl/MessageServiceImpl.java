package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.MessageService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.common.util.PageUtil;
import com.b2b.mall.db.entity.MessageVo;
import com.b2b.mall.db.mapper.sysMessageLogMapper;
import com.b2b.mall.db.mapper.sysMessageMapper;
import com.b2b.mall.db.model.sysMessage;
import com.b2b.mall.db.model.sysMessageLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/10 16:08
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private sysMessageMapper sysMessageMapper;

    @Resource
    private sysMessageLogMapper sysMessagelogMapper;


    List<MessageVo> messageVos =null;
    List<sysMessageLog> messageLogs=null;
    List<sysMessage> messages=null;

    @Override
    public List<MessageVo> findOpLogs(MessageVo messageVo, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
//        sysMessage sysMessage = new sysMessage(messageVo.getId(),messageVo.getMsgTypeId(),messageVo.getTitle(),messageVo.getType(),messageVo.getMsgType(),messageVo.getStartTime(),messageVo.getPostTime(),messageVo.getEndTime(),messageVo.getMessageText())
        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }

        int rows = sysMessageMapper.count();
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        //构造
        messageVo.setStart((pageCurrent - 1) * pageSize);
        messageVo.setEnd(pageSize);
//
//        messages = sysMessageMapper.list(sysMessage);
//        messageLogs = sysMessagelogMapper.list();
//
//        messages.forEach(messages ->{
//         messageLogs.forEach(sysMessageLog-> {
//             sysMessageLog.setId(Long.parseLong(messages.getMsgTypeId()));
//         });});

        sysMessageMapper.list(messageVo);
        messageVos.forEach(messageVo1 -> {
            try {
                BaseHTMLStringCase.isNull(messageLogs);
                BaseHTMLStringCase.isNull(messages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        messageVos.forEach(l -> {
            l.setEndTimeStr(DateUtil.preciseDate(l.getEndTime()));
            l.setPostTimeStr(DateUtil.preciseDate(l.getPostTime()));
            l.setStartTimeStr(DateUtil.preciseDate(l.getStartTime()));

        });

    //    String pageHTML = PageUtil.getPageContent("opLog_{pageCurrent}_{pageSize}_{pageCount}?id=" + logWithBlobs.getId() + "&username" + logWithBlobs.getUsername() + "&operation" + logWithBlobs.getOperation() + "&time" + logWithBlobs.getTime() + "&method" + logWithBlobs.getMethod()+"&params"+logWithBlobs.getParams()+"%ip"+logWithBlobs.getIp()+"&timeStr"+logWithBlobs.getTimeStr()+"$location"+logWithBlobs.getLocation(), pageCurrent, pageSize, pageCount);
    //    model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("messageVos", messageVos);
        return messageVos;
    }
}
