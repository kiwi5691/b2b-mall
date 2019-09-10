package com.b2b.mall.db.model;

import java.util.Date;

public class sysMessage  extends BaseObject{
    private Integer id;

    private String msgTypeId;

    private String title;

    private Integer type;

    private Integer msgType;

    private Date startTime;

    private Date postTime;

    private Date endTime;

    private String messageText;

    public sysMessage(){}
    public
    sysMessage(Integer id, String msgTypeId, String title, Integer type, Integer msgType, Date startTime, Date postTime, Date endTime, String messageText) {
        this.id = id;
        this.msgTypeId = msgTypeId;
        this.title = title;
        this.type = type;
        this.msgType = msgType;
        this.startTime = startTime;
        this.postTime = postTime;
        this.endTime = endTime;
        this.messageText = messageText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgTypeId() {
        return msgTypeId;
    }

    public void setMsgTypeId(String msgTypeId) {
        this.msgTypeId = msgTypeId == null ? null : msgTypeId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText == null ? null : messageText.trim();
    }
}