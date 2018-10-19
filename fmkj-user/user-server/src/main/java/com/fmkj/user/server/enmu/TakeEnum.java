package com.fmkj.user.server.enmu;


/**
 * @Description:   收取类型:0、用户收取;
 *
 *   1、定时任务回收;2、释放CNT时没有用户回收； 1、2在后头管理系统维护

 * @Author: youxun
 * @Version: 1.0
 **/
public enum TakeEnum {

    USER_LOST(-1,"用户扣除"),

    TYPE_USER(0, "用户收取");

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
