package com.fmkj.order.dao.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author youxun
 * @since 2018-09-03
 */
@TableName("hc_account")
public class HcAccount extends Model<HcAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 国家
     */
    private String nation;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 动态码
     */
    private String dycode;

    private Date dycodetime;
    /**
     * 头像
     */
    private String logo;
    private String nickname;
    private String email;
    private String password;
    private String name;
    private String cardnum;
    private Integer authlock;
    private Integer rid;
    private Double cnt;
    /**
     * 权重：用户等级越高权重越大，主要决定了用户月亮井能量生产得快慢
     */
    @TableField("make_weight")
    private String makeWeight;
    /**
     * 我的资产，我有多少能量
     */
    @TableField("my_p")
    private Double myP;
    /**
     * 用户的积分，积分越高等级就越高
     */
    private Integer score;
    /**
     * 等级
     */
    @TableField("grade_id")
    private Integer gradeId;
    /**
     * 0:身份未验证， 1：身份通过接口验证姓名和身份证号，2:在身份证和姓名通过验证之后上传的照片通过后台人工验证
     */
    @TableField("card_status")
    private Integer cardStatus;
    /**
     * 是否是原住民(1:是   0:不是)
     */
    private Integer isboong;
    /**
     * 锤多宝id号
     */
    private Long cdbid;

    public Double getCnt() {
        return cnt;
    }

    public void setCnt(Double cnt) {
        this.cnt = cnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDycode() {
        return dycode;
    }

    public void setDycode(String dycode) {
        this.dycode = dycode;
    }

    public Date getDycodetime() {
        return dycodetime;
    }

    public void setDycodetime(Date dycodetime) {
        this.dycodetime = dycodetime;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public Integer getAuthlock() {
        return authlock;
    }

    public void setAuthlock(Integer authlock) {
        this.authlock = authlock;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getMakeWeight() {
        return makeWeight;
    }

    public void setMakeWeight(String makeWeight) {
        this.makeWeight = makeWeight;
    }

    public Double getMyP() {
        return myP;
    }

    public void setMyP(Double myP) {
        this.myP = myP;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Integer getIsboong() {
        return isboong;
    }

    public void setIsboong(Integer isboong) {
        this.isboong = isboong;
    }

    public Long getCdbid() {
        return cdbid;
    }

    public void setCdbid(Long cdbid) {
        this.cdbid = cdbid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HcAccount{" +
        "id=" + id +
        ", nation=" + nation +
        ", telephone=" + telephone +
        ", dycode=" + dycode +
        ", dycodetime=" + dycodetime +
        ", logo=" + logo +
        ", nickname=" + nickname +
        ", email=" + email +
        ", password=" + password +
        ", name=" + name +
        ", cardnum=" + cardnum +
        ", authlock=" + authlock +
        ", rid=" + rid +
        ", makeWeight=" + makeWeight +
        ", myP=" + myP +
        ", score=" + score +
        ", gradeId=" + gradeId +
        ", cardStatus=" + cardStatus +
        ", isboong=" + isboong +
        ", cdbid=" + cdbid +
        "}";
    }
}
