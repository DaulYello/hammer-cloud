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
 * @since 2018-10-16
 */
@TableName("fm_integral_info")
public class FmIntegralInfo extends Model<FmIntegralInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 每个阶段生成的R积分数量
     */
    @TableField("integral_num")
    private Double integralNum;
    /**
     * 0未收取状态; 1自已收取; 2系统回收;-1被好友偷走
     */
    private Integer status;
    /**
     * R积分生成时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 更新时间
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

    public Double getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(Double integralNum) {
        this.integralNum = integralNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "FmIntegralInfo{" +
        "id=" + id +
        ", uid=" + uid +
        ", integralNum=" + integralNum +
        ", status=" + status +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
