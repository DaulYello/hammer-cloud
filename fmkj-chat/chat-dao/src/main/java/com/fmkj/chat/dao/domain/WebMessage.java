package com.fmkj.chat.dao.domain;

/**
 * @Author: youxun
 * @Date: 2018/8/31 09:28
 * @Description:
 */
public class WebMessage {

    private Integer id;

    private String text;

    private String createdAt;

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
