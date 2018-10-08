package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author youxun
 * @since 2018-09-21
 */
@TableName("hc_assets")
public class HcAssets extends Model<HcAssets> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户的id，对应hc_account中的id
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 钱包地址
     */
    private String walletfile;
    /**
     * keystore文件
     */
    private String keystore;
    /**
     * 助记词备份状态0否，1是
     */
    private Integer status;
    /**
     * 资产种类,有两种HRC，ECH
     */
    private String assetskind;
    /**
     * 创建时间
     */
    private Date createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWalletfile() {
        return walletfile;
    }

    public void setWalletfile(String walletfile) {
        this.walletfile = walletfile;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAssetskind() {
        return assetskind;
    }

    public void setAssetskind(String assetskind) {
        this.assetskind = assetskind;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HcAssets{" +
        "id=" + id +
        ", userId=" + userId +
        ", walletfile=" + walletfile +
        ", keystore=" + keystore +
        ", status=" + status +
        ", assetskind=" + assetskind +
        ", createtime=" + createtime +
        "}";
    }
}
