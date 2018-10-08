package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：17:28 2018/8/29 0029
 * @ Description：用户黑白名单实体
 */
@TableName("bm_list")
public class BmList extends Model<BmList> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id--hc_account
     */
    private Integer uid;

    /**
     * 名单状态  0无  1白名单  2黑名单
     */
    private Integer status;

    /**
     * 黑名单限制用户登录APP，0可以登录，1，不能登录
     */
    private Integer limitLogin;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLimitLogin() {
        return limitLogin;
    }

    public void setLimitLogin(Integer limitLogin) {
        this.limitLogin = limitLogin;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
