package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Date;

/**
 * 用户的实名信息
 * @author huangs
 * @since 2018-09-19
 */
public class UserRealInfo {

    //hc_account 中的信息
    private Integer id;
    /**
     * 用户的真实名字
     */
    private String name;
    /**
     * 用户的身份号码
     */
    private String cardnum;


    //hc_userimage中的信息
    /**
     * 照片路径
     */
    private String url;
    /**
     * 正面照名字
     */
    private String fullPhoto;
    /**
     * 反面照片名字
     */
    private String reversePhoto;
    /**
     * 身份认证时间
     */
    private Date time;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullPhoto() {
        return fullPhoto;
    }

    public void setFullPhoto(String fullPhoto) {
        this.fullPhoto = fullPhoto;
    }

    public String getReversePhoto() {
        return reversePhoto;
    }

    public void setReversePhoto(String reversePhoto) {
        this.reversePhoto = reversePhoto;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
