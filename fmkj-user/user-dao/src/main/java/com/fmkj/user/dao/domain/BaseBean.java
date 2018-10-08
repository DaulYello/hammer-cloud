package com.fmkj.user.dao.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class BaseBean {
	private Integer uid;// 用户id

	private Integer fid;// 朋友id

	private String token;

	private String useToolname;// 用户道具名

	private String nickname;// 用户昵称

	private Date time;// 时间

	private MultipartFile file;// 传入的文件

	private Integer status;// 证件类型/状态

	private Integer invitingNum;// 周排行榜中用户邀请的人数

	private Integer points;// 周排行榜中用户邀请人获得的积分核和

	private Integer id;//周排行榜中的id
	
	private String logo;//周排行榜中的logo(头像)
	
	
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInvitingNum() {
		return invitingNum;
	}

	public void setInvitingNum(Integer invitingNum) {
		this.invitingNum = invitingNum;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUseToolname() {
		return useToolname;
	}

	public void setUseToolname(String useToolname) {
		this.useToolname = useToolname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "BaseBean [uid=" + uid + ", fid=" + fid + ", token=" + token + ", useToolname=" + useToolname
				+ ", nickname=" + nickname + "]";
	}

}
