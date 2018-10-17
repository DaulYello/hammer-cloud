package com.fmkj.user.dao.queryVo;

public class CntRVo {

    private Integer rId;

    private Integer uid;

    private Integer friendId;

    private Integer cntId;

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Integer getCntId() {
        return cntId;
    }

    public void setCntId(Integer cntId) {
        this.cntId = cntId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }
}
