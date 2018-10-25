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
@TableName("fm_recyle_log")
public class FmRecyleLog extends Model<FmRecyleLog> {

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

    @TableField("friend_id")
    private Integer friendId;
    /**
     * 收取\回收CNT或R积分数量
     */
    @TableField("take_num")
    private Double takeNum;
    /**
     * 收取\回收时间
     */
    @TableField("take_date")
    private Date takeDate;
    /**
     * 获取渠道:0、用户自己收取;1、定时任务回收;2、释放CNT时没有用户回收;3、未中锤奖励;4;周排行奖励;5被用户偷取;6参与活动扣除;
     */
    @TableField("take_type")
    private Integer takeType;
    /**
     * 所属类型1、CNT; 2、R积分
     */
    @TableField("recyle_type")
    private Integer recyleType;

    @TableField("take_msg")
    private String takeMsg;

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getTakeMsg() {
        return takeMsg;
    }

    public void setTakeMsg(String takeMsg) {
        this.takeMsg = takeMsg;
    }

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

    public Double getTakeNum() {
        return takeNum;
    }

    public void setTakeNum(Double takeNum) {
        this.takeNum = takeNum;
    }

    public Date getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }

    public Integer getTakeType() {
        return takeType;
    }

    public void setTakeType(Integer takeType) {
        this.takeType = takeType;
    }

    public Integer getRecyleType() {
        return recyleType;
    }

    public void setRecyleType(Integer recyleType) {
        this.recyleType = recyleType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FmRecyleLog{" +
        "id=" + id +
        ", uid=" + uid +
        ", takeNum=" + takeNum +
        ", takeDate=" + takeDate +
        ", takeType=" + takeType +
        ", recyleType=" + recyleType +
        "}";
    }
}
