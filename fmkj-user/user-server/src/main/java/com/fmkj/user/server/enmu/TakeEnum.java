package com.fmkj.user.server.enmu;


/**
 * @Description:  获取渠道:0、用户自己收取;1、定时任务回收;2、释放CNT时没有用户回收;3、未中锤奖励;4;周排行奖励;5被用户偷取;6参与活动扣除;7邀请好友获得;8确认收货后获取;
 * @Author: youxun
 * @Version: 1.0
 **/
public enum TakeEnum {

    USER_GET(0,"用户获得"),

    TYPE_TASK(1,"定时任务回收"),

    TYPE_ALLOT(2, "释放时没有用户的情况回收"),

    NO_WIN(3, "未中锤奖励"),

    RANKING_ALLOT(4, "周排行奖励"),

    USER_STEAL(5, "被用户偷取"),

    PART_ACITIVITY(6, "参与活动扣除"),

    INVITE_FRIEND(7, "邀请好友获得"),

    TAKE_GOODS(8, "确认收货后获取得");

    public int status;

    public String msg;


    private TakeEnum(int status, String msg){
        this.status = status;
        this.msg = msg;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
