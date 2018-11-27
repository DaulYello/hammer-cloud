package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @since 2018-11-27
 */
@TableName("pm_extend_value")
public class PmExtendValue extends Model<PmExtendValue> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 参与记录ID
     */
    private Integer pid;
    /**
     * 任务扩展ID
     */
    private Integer xid;
    /**
     * 扩展字段的值
     */
    private String xvalue;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }

    public String getXvalue() {
        return xvalue;
    }

    public void setXvalue(String xvalue) {
        this.xvalue = xvalue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PmExtendValue{" +
        "id=" + id +
        ", pid=" + pid +
        ", xid=" + xid +
        ", xvalue=" + xvalue +
        ", createDate=" + createDate +
        "}";
    }
}
