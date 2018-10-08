package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author youxun
 * @since 2018-09-21
 */
@TableName("hc_moonwell")
public class HcMoonwell extends Model<HcMoonwell> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    @TableField("get_p")
    private String getP;
    @TableField("lose_p")
    private String loseP;
    /**
     * 月亮井当前能量
     */
    @TableField("current_p")
    private Double currentP;
    /**
     * 一个小时生产的能量值，每小时的生产速率
     */
    @TableField("a_h_c_p")
    private Double aHCP;
    /**
     * 当前井里被丢了石头的个数
     */
    private Integer rocknum;
    private Integer moonnum;
    @TableField("friend_can_steal_current_p")
    private Double friendCanStealCurrentP;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getGetP() {
        return getP;
    }

    public void setGetP(String getP) {
        this.getP = getP;
    }

    public String getLoseP() {
        return loseP;
    }

    public void setLoseP(String loseP) {
        this.loseP = loseP;
    }

    public Double getCurrentP() {
        return currentP;
    }

    public void setCurrentP(Double currentP) {
        this.currentP = currentP;
    }

    public Double getaHCP() {
        return aHCP;
    }

    public void setaHCP(Double aHCP) {
        this.aHCP = aHCP;
    }

    public Integer getRocknum() {
        return rocknum;
    }

    public void setRocknum(Integer rocknum) {
        this.rocknum = rocknum;
    }

    public Integer getMoonnum() {
        return moonnum;
    }

    public void setMoonnum(Integer moonnum) {
        this.moonnum = moonnum;
    }

    public Double getFriendCanStealCurrentP() {
        return friendCanStealCurrentP;
    }

    public void setFriendCanStealCurrentP(Double friendCanStealCurrentP) {
        this.friendCanStealCurrentP = friendCanStealCurrentP;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HcMoonwell{" +
        "id=" + id +
        ", uid=" + uid +
        ", getP=" + getP +
        ", loseP=" + loseP +
        ", currentP=" + currentP +
        ", aHCP=" + aHCP +
        ", rocknum=" + rocknum +
        ", moonnum=" + moonnum +
        ", friendCanStealCurrentP=" + friendCanStealCurrentP +
        "}";
    }
}
