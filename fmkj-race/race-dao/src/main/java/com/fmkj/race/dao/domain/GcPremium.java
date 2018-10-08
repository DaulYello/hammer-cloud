package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 溢价区间表
 * </p>
 *
 * @author ru
 * @since 2018-09-03
 */
@TableName("gc_premium")
public class GcPremium extends Model<GcPremium> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 溢价区间最大值，最小值为0
     */
    private Integer premax;
    /**
     * 积分最小值
     */
    private Integer integralmin;
    /**
     * 积分最大值
     */
    private Integer integralmax;
    /**
     * 溢价区间默认值
     */
    private Double defaultval;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getPremax() {
        return premax;
    }

    public void setPremax(Integer premax) {
        this.premax = premax;
    }

    public Integer getIntegralmin() {
        return integralmin;
    }

    public void setIntegralmin(Integer integralmin) {
        this.integralmin = integralmin;
    }

    public Integer getIntegralmax() {
        return integralmax;
    }

    public void setIntegralmax(Integer integralmax) {
        this.integralmax = integralmax;
    }

    public Double getDefaultval() {
        return defaultval;
    }

    public void setDefaultval(Double defaultval) {
        this.defaultval = defaultval;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "GcPremium{" +
        "Id=" + Id +
        ", premax=" + premax +
        ", integralmin=" + integralmin +
        ", integralmax=" + integralmax +
        ", defaultval=" + defaultval +
        "}";
    }
}
