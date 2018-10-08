package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 日志表实体
 * </p>
 *
 * @author yangshengbin
 * @since 2018-08-31
 */
@TableName("sys_operate_log")
public class SysOperateLog extends Model<SysOperateLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * ϵͳģ
     */
    private String module;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_time")
    private Date createTime;
    @TableField("operate_desc")
    private String operateDesc;
    @TableField("exception_msg")
    private String exceptionMsg;
    @TableField("operate_status")
    private Long operateStatus;
    @TableField("request_method")
    private String requestMethod;
    @TableField("request_params")
    private String requestParams;
    @TableField("request_host")
    private String requestHost;
    @TableField("request_class")
    private String requestClass;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperateDesc() {
        return operateDesc;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public Long getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Long operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestHost() {
        return requestHost;
    }

    public void setRequestHost(String requestHost) {
        this.requestHost = requestHost;
    }

    public String getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(String requestClass) {
        this.requestClass = requestClass;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysOperateLog{" +
        "id=" + id +
        ", module=" + module +
        ", userId=" + userId +
        ", createTime=" + createTime +
        ", operateDesc=" + operateDesc +
        ", exceptionMsg=" + exceptionMsg +
        ", operateStatus=" + operateStatus +
        ", requestMethod=" + requestMethod +
        ", requestParams=" + requestParams +
        ", requestHost=" + requestHost +
        ", requestClass=" + requestClass +
        "}";
    }
}
