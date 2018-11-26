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
 * @since 2018-11-21
 */
@TableName("pm_part")
public class PmPart extends Model<PmPart> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 参与用户
     */
    private Integer uid;
    /**
     * 任务id
     */
    private Integer tid;
    /**
     * 审核状态0,未审核;1、待审核; 2、审核通过;-1、驳回
     */
    @TableField("audit_status")
    private Integer auditStatus;
    /**
     * 审核意见
     */
    @TableField("audit_option")
    private String auditOption;
    /**
     * 提交电话
     */
    private String telephone;
    /**
     * 参与时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 审核时间
     */
    @TableField("update_date")
    private Date updateDate;


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

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditOption() {
        return auditOption;
    }

    public void setAuditOption(String auditOption) {
        this.auditOption = auditOption;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        return "PmPart{" +
        "id=" + id +
        ", uid=" + uid +
        ", tid=" + tid +
        ", auditStatus=" + auditStatus +
        ", auditOption=" + auditOption +
        ", telephone=" + telephone +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
