package com.fmkj.order.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: youxun
 * @Date: 2018/8/29 09:56
 * @Description:
 */
@TableName("sys_operate_log")
public class OperateLog extends Model<OperateLog> implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

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
    private Integer operateStatus;

    @TableField("request_method")
    private String requestMethod;

    @TableField("request_params")
    private String requestparams;

    @TableField("request_class")
    private String requestClass;

    @TableField("request_host")
    private String requestHost;

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

    public Integer getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Integer operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestparams() {
        return requestparams;
    }

    public void setRequestparams(String requestparams) {
        this.requestparams = requestparams;
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
}
