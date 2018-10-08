package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author yangshengbin
 * @since 2018-08-30
 */
@TableName("gc_message")
public class GcMessage extends Model<GcMessage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
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


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

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

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "GcMessage{" +
        "Id=" + Id +
        ", message=" + message +
        ", time=" + time +
        ", type=" + type +
        "}";
    }
}
