package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.redis.RedisManager;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisManager redisManager;
    @Resource
    private sysMessageMapper sysMessageMapper;


    List<MessageVo> messageVos =null;
    @Override
    public List<MessageVo> findOpLogs(MessageVo messageVo, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
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

        messageVos = null;

        messageVos=(List<MessageVo>)redisManager.getList("messageVos");

        if(messageVos==null){
            messageVos= sysMessageMapper.list(messageVo);
            redisManager.setList("messageVos",messageVos);
        }
        messageVos.forEach(l -> {
            l.setEndTimeStr(DateUtil.preciseDate(l.getEndTime()));
            l.setPostTimeStr(DateUtil.preciseDate(l.getPostTime()));
            l.setStartTimeStr(DateUtil.preciseDate(l.getStartTime()));
            l.setStatusStr(BaseHTMLStringCase.statusStrTo(String.valueOf(l.getStatus())));
            l.setMsgTypeStr(BaseHTMLStringCase.MsgtypeStrTo(String.valueOf(l.getMsgType())));
            l.setRecIdStr(BaseHTMLStringCase.recIdStrTo(String.valueOf(l.getRecId())));
            l.setTypeStr(BaseHTMLStringCase.typeStrTo(String.valueOf(l.getType())));
        });

        String pageHTML = PageUtil.getPageContent("opLog_{pageCurrent}_{pageSize}_{pageCount}?id=" + messageVo.getId() + "&msgTypeId" + messageVo.getMsgTypeId() + "&title" + messageVo.getTitle() + "&messageText" + messageVo.getMessageText() + "&recId" + messageVo.getRecId()+"&sendId"+messageVo.getSendId()+"&email"+messageVo.getEmail()+"&statusStr"+messageVo.getStatusStr()+"&groupId"+messageVo.getGroupId()+"&startTimeStr"+messageVo.getStatusStr(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("messageVos", messageVos);
        return messageVos;
    }
}
