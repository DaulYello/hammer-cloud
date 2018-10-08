package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 积分类别表
 * </p>
 *
 * @author youxun
 * @since 2018-09-17
 */
@TableName("hc_points_class")
public class HcPointsClass extends Model<HcPointsClass> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 积分类别名称
     */
    private String name;
    /**
     * 描述
     */
    private String describe;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "HcPointsClass{" +
        "Id=" + Id +
        ", name=" + name +
        ", describe=" + describe +
        "}";
    }
}
