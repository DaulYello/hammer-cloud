package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Locale;

/**
 * <p>
 * 积分记录表
 * </p>
 *
 * @author youxun
 * @since 2018-09-17
 */
@TableName("hc_points_record")
public class HcPointsRecord extends Model<HcPointsRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 对应hc_points_class表中的主键
     */
    @TableField("points_id")
    private Integer pointsId;
    /**
     * 得到的积分数
     */
    @TableField("points_num")
    private Double pointsNum;
    /**
     * 时间
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

    public Integer getPointsId() {
        return pointsId;
    }

    public void setPointsId(Integer pointsId) {
        this.pointsId = pointsId;
    }

    public Double getPointsNum() {
        return pointsNum;
    }

    public void setPointsNum(Double pointsNum) {
        this.pointsNum = pointsNum;
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
        return "HcPointsRecord{" +
        "Id=" + Id +
        ", uid=" + uid +
        ", pointsId=" + pointsId +
        ", pointsNum=" + pointsNum +
        ", time=" + time +
        "}";
    }
}
