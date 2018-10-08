package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 活动物流表
 * </p>
 *
 * @author yangshengbin
 * @since 2018-08-31
 */
@TableName("gc_order")
public class GcOrder extends Model<GcOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 活动id
     */
    private Integer aid;
    /**
     * 物流名称
     */
    private String name;
    /**
     * 物流单号
     */
    private String number;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "GcOrder{" +
        "Id=" + Id +
        ", aid=" + aid +
        ", name=" + name +
        ", number=" + number +
        "}";
    }
}
