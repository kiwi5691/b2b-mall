package com.b2b.mall.db.entity;

import com.b2b.mall.db.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * @auther kiwi
 * @Date 2019/9/8 22:43
 */
@Data
public class MessageVo  extends BaseObject {
    private Integer id;

    private String msgTypeId;

    private String title;

    private Integer type;

    private Integer msgType;

    private Date startTime;

    private Date postTime;

    private Date endTime;

    private String messageText;

    private String recId;

    private String sendId;

    private String email;

    private Integer status;

    private String groupId;

    private String startTimeStr;

    private String postTimeStr;

    private String endTimeStr;
    private String statusStr;
    private String msgTypeStr;
    private String recIdStr;
    private String typeStr;

}
