package com.fmkj.race.dao.dto;

import com.fmkj.race.dao.domain.GcJoinactivityrecord;

/**
 * 用户参与记录分页 yangshengbin
 */
public class JoinActivityDto extends GcJoinactivityrecord {

    //用户头像路径
    private String logo;

    //用户昵称
    private String nickname;

    //用户成长力
    private Integer score;

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
}
