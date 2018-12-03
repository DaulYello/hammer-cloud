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
@TableName("pm_extend")
public class PmExtend extends Model<PmExtend> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务ID
     */
    private Integer tid;
    /**
     * 列名
     */
    @TableField("clounm_key")
    private String clounmKey;
    /**
     * 列名名称
     */
    @TableField("clounm_name")
    private String clounmName;
    /**
     * 提示信息
     */
    @TableField("clounm_tip")
    private String clounmTip;
    /**
     * 是否为空0、可为空, 1、必填
     */
    @TableField("is_empty")
    private Integer isEmpty;
    /**
     * 为空提示
     */
    @TableField("empty_hint")
    private String emptyHint;
    /**
     * 显示顺序
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getClounmKey() {
        return clounmKey;
    }

    public void setClounmKey(String clounmKey) {
        this.clounmKey = clounmKey;
    }

    public String getClounmName() {
        return clounmName;
    }

    public void setClounmName(String clounmName) {
        this.clounmName = clounmName;
    }

    public String getClounmTip() {
        return clounmTip;
    }

    public void setClounmTip(String clounmTip) {
        this.clounmTip = clounmTip;
    }

    public Integer getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Integer isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getEmptyHint() {
        return emptyHint;
    }

    public void setEmptyHint(String emptyHint) {
        this.emptyHint = emptyHint;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PmExtend{" +
        "id=" + id +
        ", tid=" + tid +
        ", clounmKey=" + clounmKey +
        ", clounmName=" + clounmName +
        ", clounmTip=" + clounmTip +
        ", isEmpty=" + isEmpty +
        ", emptyHint=" + emptyHint +
        ", orderNum=" + orderNum +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
