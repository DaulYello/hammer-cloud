package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 道具表
 * </p>
 *
 * @author youxun
 * @since 2018-09-21
 */
@TableName("hc_tools")
public class HcTools extends Model<HcTools> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 月光数量
     */
    private Integer totalmoon;
    /**
     * 石头数量
     */
    private Integer totalrock;


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

    public Integer getTotalmoon() {
        return totalmoon;
    }

    public void setTotalmoon(Integer totalmoon) {
        this.totalmoon = totalmoon;
    }

    public Integer getTotalrock() {
        return totalrock;
    }

    public void setTotalrock(Integer totalrock) {
        this.totalrock = totalrock;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "HcTools{" +
        "Id=" + Id +
        ", uid=" + uid +
        ", totalmoon=" + totalmoon +
        ", totalrock=" + totalrock +
        "}";
    }
}
