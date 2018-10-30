package com.fmkj.user.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("hc_account")
public class HcAccount extends Model<HcAccount> implements Serializable{
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String nation;

	private String telephone;

	private String dycode;

	private Date dycodetime;

	private String logo;

	private String nickname;

	private String email;

	private String password;

	private String name;

	private String cardnum;

	@TableField("realnam_info")
	private String realnamInfo;

	private Boolean authlock;

	private Integer rid;

	private Integer makeWeight;

	private Double myP;

	private Integer score;// 用户积分

	private Integer gradeId;// 用户等级
	
	private Integer cardStatus;// 用户身份验证是否通过的标志
	
	private Integer isboong;//是否是原住民  1-->是  0--->不是
	
	private Long cdbid;//锤多宝id

	private Double cnt;

	//注册时间
	@TableField("create_date")
	private Date createDate;
	/**
	 * 更新时间
	 */
	@TableField("update_date")
	private Date updateDate;

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

	public Double getCnt() {
		return cnt;
	}

	public void setCnt(Double cnt) {
		this.cnt = cnt;
	}

	public String getRealnamInfo() {
		return realnamInfo;
	}

	public void setRealnamInfo(String realnamInfo) {
		this.realnamInfo = realnamInfo;
	}

	public Long getCdbid() {
		return cdbid;
	}

	public void setCdbid(Long cdbid) {
		this.cdbid = cdbid;
	}

	public Integer getIsboong() {
		return isboong;
	}

	public void setIsboong(Integer isboong) {
		this.isboong = isboong;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}
	
	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Double getMyP() {
		return myP;
	}

	public void setMyP(Double myP) {
		this.myP = myP;
	}

	public Integer getMakeWeight() {
		return makeWeight;
	}

	public void setMakeWeight(Integer makeWeight) {
		this.makeWeight = makeWeight;
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return nation
	 */
	public String getNation() {
		return nation;
	}

	/**
	 * @param nation
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}

	/**
	 * @return telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return dycode
	 */
	public String getDycode() {
		return dycode;
	}

	/**
	 * @param dycode
	 */
	public void setDycode(String dycode) {
		this.dycode = dycode;
	}

	/**
	 * @return dycodetime
	 */
	public Date getDycodetime() {
		return dycodetime;
	}

	/**
	 * @param dycodetime
	 */
	public void setDycodetime(Date dycodetime) {
		this.dycodetime = dycodetime;
	}

	/**
	 * @return logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return cardnum
	 */
	public String getCardnum() {
		return cardnum;
	}

	/**
	 * @param cardnum
	 */
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	/**
	 * @return authlock
	 */
	public Boolean getAuthlock() {
		return authlock;
	}

	/**
	 * @param authlock
	 */
	public void setAuthlock(Boolean authlock) {
		this.authlock = authlock;
	}

	/**
	 * @return rid
	 */
	public Integer getRid() {
		return rid;
	}

	/**
	 * @param rid
	 */
	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "HcAccount [id=" + id + ", nation=" + nation + ", telephone=" + telephone + ", dycode=" + dycode
				+ ", dycodetime=" + dycodetime + ", logo=" + logo + ", nickname=" + nickname + ", email=" + email
				+ ", password=" + password + ", name=" + name + ", cardnum=" + cardnum + ", authlock=" + authlock
				+ ", rid=" + rid + ", makeWeight=" + makeWeight + ", myP=" + myP + ", score=" + score + ", gradeId="
				+ gradeId + ", cardStatus=" + cardStatus + ", isboong=" + isboong + ", cdbid=" + cdbid + ", getCdbid()="
				+ getCdbid() + ", getIsboong()=" + getIsboong() + ", getCardStatus()=" + getCardStatus()
				+ ", getGradeId()=" + getGradeId() + ", getScore()=" + getScore() + ", getMyP()=" + getMyP()
				+ ", getMakeWeight()=" + getMakeWeight() + ", getId()=" + getId() + ", getNation()=" + getNation()
				+ ", getTelephone()=" + getTelephone() + ", getDycode()=" + getDycode() + ", getDycodetime()="
				+ getDycodetime() + ", getLogo()=" + getLogo() + ", getNickname()=" + getNickname() + ", getEmail()="
				+ getEmail() + ", getPassword()=" + getPassword() + ", getName()=" + getName() + ", getCardnum()="
				+ getCardnum() + ", getAuthlock()=" + getAuthlock() + ", getRid()=" + getRid() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}