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
@TableName("pm_strategy")
public class PmStrategy extends Model<PmStrategy> {

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
     * 攻略
     */
    private String strategy;
    /**
     * 顺序
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
    /**
     * 图片ID
     */
    private Integer imageId;


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

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
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
        return "PmStrategy{" +
        "id=" + id +
        ", tid=" + tid +
        ", strategy=" + strategy +
        ", orderNum=" + orderNum +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", imageId=" + imageId +
        "}";
    }
}
