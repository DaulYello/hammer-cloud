package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * @program: fmkj
 * @description: Session信息实体类
 * @author: 杨胜彬
 * @create: 2018-08-27 14:20
 **/
@TableName("hc_session")
public class HcSession extends Model<HcSession> implements Serializable{

    //快速导入jar包:alt+enter
    //快速生成get/set:alt+insert

    /**
     * session表主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * token字符
     */
    private String token;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 开始时间
     */
    private Date btime;

    /**
     * 结束时间
     */
    private Date etime;

    /**
     *
     */
    private Date ltime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getBtime() {
        return btime;
    }

    public void setBtime(Date btime) {
        this.btime = btime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public Date getLtime() {
        return ltime;
    }

    public void setLtime(Date ltime) {
        this.ltime = ltime;
    }


    @Override
    public String toString() {
        return "HcSession{" +
                "id=" + id +
                ", token='" + token +
                ", uid=" + uid +
                ", btime=" + btime +
                ", etime=" + etime +
                ", ltime=" + ltime +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
