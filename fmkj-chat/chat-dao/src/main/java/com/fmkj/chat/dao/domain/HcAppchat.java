package com.fmkj.chat.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author youxun
 * @since 2018-11-13
 */
@TableName("hc_appchat")
public class HcAppchat extends Model<HcAppchat> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发送消息用户ID
     */
    @TableField("send_id")
    private Integer sendId;
    /**
     * 接受消息用户ID
     */
    @TableField("accept_id")
    private Integer acceptId;

    /**
     * 0,未读; 1,已读
     */
    private Integer status;
    /**
     * 消息内容
     */
    private String text;
    /**
     * 消息产生时间
     */
    @TableField("create_date")
    private Date createDate;

    @TableField("each_label")
    private String eachLabel;

    public String getEachLabel() {
        return eachLabel;
    }

    public void setEachLabel(String eachLabel) {
        this.eachLabel = eachLabel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public Integer getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Integer acceptId) {
        this.acceptId = acceptId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HcAppchat{" +
        "id=" + id +
        ", sendId=" + sendId +
        ", acceptId=" + acceptId +
        ", text=" + text +
        ", createDate=" + createDate +
        "}";
    }
}
