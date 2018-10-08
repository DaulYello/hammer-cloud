package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 参与活动的记录表
 * </p>
 *
 * @author yangshengbin
 * @since 2018-09-05
 */
@TableName("gc_joinactivityrecord")
public class GcJoinactivityrecord extends Model<GcJoinactivityrecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 参与活动的用户id
     */
    private Integer uid;
    /**
     * 对应活动表activity中的主键
     */
    private Integer aid;
    /**
     * 是否上链
     */
    private Integer ischain;
    /**
     * 参与活动的时间
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

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getIschain() {
        return ischain;
    }

    public void setIschain(Integer ischain) {
        this.ischain = ischain;
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
        return "GcJoinactivityrecord{" +
        "Id=" + Id +
        ", uid=" + uid +
        ", aid=" + aid +
        ", ischain=" + ischain +
        ", time=" + time +
        "}";
    }
}
