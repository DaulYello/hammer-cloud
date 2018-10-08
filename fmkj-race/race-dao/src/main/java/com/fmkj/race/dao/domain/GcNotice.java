package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 通知信息实体
 * </p>
 *
 * @author yangshengbin
 * @since 2018-08-30
 */
@TableName("gc_notice")
public class GcNotice extends Model<GcNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 消息id
     */
    private Integer mid;
    /**
     * 已读标识 1未读 0已读
     */
    private Integer flag;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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
        return "GcNotice{" +
        "Id=" + Id +
        ", uid=" + uid +
        ", mid=" + mid +
        ", flag=" + flag +
        "}";
    }

}
