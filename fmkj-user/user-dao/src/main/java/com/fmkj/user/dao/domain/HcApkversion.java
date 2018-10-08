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
 * 版本升级表
 * </p>
 *
 * @author youxun
 * @since 2018-09-20
 */
@TableName("hc_apkversion")
public class HcApkversion extends Model<HcApkversion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * apk安装包再服务器上的文件夹地址
     */
    @TableField("apk_url")
    private String apkUrl;
    /**
     * apk安装包的版本号
     */
    @TableField("apk_version")
    private String apkVersion;
    /**
     * 升级的内容说明
     */
    @TableField("upgrade_msg")
    private String upgradeMsg;
    /**
     * 版本升级的描述
     */
    private Date time;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "HcApkversion{" +
        "Id=" + Id +
        ", apkUrl=" + apkUrl +
        ", apkVersion=" + apkVersion +
        ", upgradeMsg=" + upgradeMsg +
        ", time=" + time +
        "}";
    }
}
