package com.fmkj.race.dao.dto;

import com.fmkj.race.dao.domain.GcNotice;

import java.util.Date;

public class NoticeQueryDto extends GcNotice {
    /**
     * 通知内容
     */
    private String message;
    /**
     * 通知时间
     */
    private Date time;
    /**
     * 消息类型 2广播 1组 0个人
     */
    private Integer type;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }



}
