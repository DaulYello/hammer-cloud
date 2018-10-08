package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 活动（竟锤）类型表
 * </p>
 *
 * @author 杨胜彬
 * @since 2018-09-04
 */
@TableName("gc_activitytype")
public class GcActivitytype extends Model<GcActivitytype> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 活动的类型
     */
    private String type;
    /**
     * 持续天数
     */
    private Integer days;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "GcActivitytype{" +
        "Id=" + Id +
        ", type=" + type +
        ", days=" + days +
        "}";
    }
}
