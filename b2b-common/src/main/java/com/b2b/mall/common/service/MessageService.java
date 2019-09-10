package com.b2b.mall.common.service;

import com.b2b.mall.db.entity.MessageVo;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/10 16:08
 */
public interface MessageService {
    List<MessageVo> findOpLogs(MessageVo messageVo, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);

}
