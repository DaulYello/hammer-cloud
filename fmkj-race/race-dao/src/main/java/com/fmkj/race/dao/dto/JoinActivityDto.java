package com.fmkj.race.dao.dto;

import com.fmkj.race.dao.domain.GcJoinactivityrecord;

/**
 * 用户参与记录分页
 */
public class JoinActivityDto extends GcJoinactivityrecord {

    //用户头像路径
    private String logo;

    //用户昵称
    private String nickname;

    //用户成长力
    private Integer score;

    /**
     * 参与活动人数
     */
    private Integer num;

    /**
     * 票面值
     */
    private Double par;

    private String contract;

    /**
     * 对应活动类型表gc_activitytype中的主键
     */
    private Integer typeid;

    /**
     * 产品的名称
     */
    private String pname;

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPar() {
        return par;
    }

    public void setPar(Double par) {
        this.par = par;
    }
}
