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
@TableName("pm_task")
public class PmTask extends Model<PmTask> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务标题
     */
    private String title;
    /**
     * 任务目标
     */
    @TableField("task_target")
    private String taskTarget;
    /**
     * 二级描述
     */
    @TableField("sub_desc")
    private String subDesc;
    /**
     * 任务奖励
     */
    private Double reward;
    /**
     * 审核周期
     */
    @TableField("audit_cycle")
    private String auditCycle;
    /**
     * 是否需要下载APP:0,不需要;1、需要
     */
    private Integer type;
    /**
     * 打开地址
     */
    @TableField("down_url")
    private String downUrl;
    /**
     * 任务开始时间
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 任务结束时间
     */
    @TableField("end_date")
    private Date endDate;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 任务更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 状态0、正常;-1、已删除
     */
    private Integer status;
    /**
     * 任务标题图片
     */
    private Integer logoId;
    /**
     * 详情图片
     */
    private Integer imageId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskTarget() {
        return taskTarget;
    }

    public void setTaskTarget(String taskTarget) {
        this.taskTarget = taskTarget;
    }

    public String getSubDesc() {
        return subDesc;
    }

    public void setSubDesc(String subDesc) {
        this.subDesc = subDesc;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public String getAuditCycle() {
        return auditCycle;
    }

    public void setAuditCycle(String auditCycle) {
        this.auditCycle = auditCycle;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLogoId() {
        return logoId;
    }

    public void setLogoId(Integer logoId) {
        this.logoId = logoId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PmTask{" +
        "id=" + id +
        ", title=" + title +
        ", taskTarget=" + taskTarget +
        ", subDesc=" + subDesc +
        ", reward=" + reward +
        ", auditCycle=" + auditCycle +
        ", type=" + type +
        ", downUrl=" + downUrl +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", status=" + status +
        ", logoId=" + logoId +
        ", imageId=" + imageId +
        "}";
    }
}
