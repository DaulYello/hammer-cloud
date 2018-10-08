package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户头像上传表
 * </p>
 *
 * @author youxun
 * @since 2018-09-17
 */
@TableName("hc_userhead")
public class HcUserhead extends Model<HcUserhead> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    private Integer uid;
    /**
     * 存放头像照片地址
     */
    private String url;
    /**
     * 照片名
     */
    private String name;
    /**
     * 存放时间
     */
    private Date time;
    /**
     * 照片状态
     */
    private Integer status;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "HcUserhead{" +
        "Id=" + Id +
        ", uid=" + uid +
        ", url=" + url +
        ", name=" + name +
        ", time=" + time +
        ", status=" + status +
        "}";
    }
}
