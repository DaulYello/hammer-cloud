package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @author yangshengbin
 * @Description：活动图片实体
 * @date 2018/8/30 0030 11:33
 * @param null
 * @return
*/
@TableName("gc_pimage")
public class GcPimage extends Model<GcPimage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 活动id
     */
    private Integer aid;
    /**
     * 产品图片
     */
    private String imageurl;
    /**
     * 图片标识
     */
    private Integer flag;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "GcPimage{" +
        "Id=" + Id +
        ", aid=" + aid +
        ", imageurl=" + imageurl +
        ", flag=" + flag +
        "}";
    }
}
