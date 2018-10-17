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
 * 
 * </p>
 *
 * @author youxun
 * @since 2018-10-17
 */
@TableName("gc_assets")
public class GcAssets extends Model<GcAssets> {

    private static final long serialVersionUID = 1L;

    /**
     * 活动支持记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动id
     */
    private Integer aid;
    /**
     * 活动溢价后的总资产
     */
    @TableField("total_assets")
    private Double totalAssets;
    /**
     * 手续费
     */
    private Double poundage;
    /**
     * 数据记录时间
     */
    @TableField("create_date")
    private Date createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Double getPoundage() {
        return poundage;
    }

    public void setPoundage(Double poundage) {
        this.poundage = poundage;
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
        return "GcAssets{" +
        "id=" + id +
        ", aid=" + aid +
        ", totalAssets=" + totalAssets +
        ", poundage=" + poundage +
        ", createDate=" + createDate +
        "}";
    }
}
