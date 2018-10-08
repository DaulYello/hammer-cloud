package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 反馈意见表
 * </p>
 *
 * @author youxun
 * @since 2018-09-20
 */
@TableName("hc_feedback")
public class HcFeedback extends Model<HcFeedback> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    private Integer uid;
    /**
     * 反馈意见内容
     */
    private String msg;
    /**
     * 反馈时间
     */
    private Date time;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "HcFeedback{" +
        "Id=" + Id +
        ", uid=" + uid +
        ", msg=" + msg +
        ", time=" + time +
        "}";
    }
}
