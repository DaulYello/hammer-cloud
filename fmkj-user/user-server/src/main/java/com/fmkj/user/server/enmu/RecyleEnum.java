package com.fmkj.user.server.enmu;


/**
 * @Description: 所属类型1、CNT; 2、R积分
 * @Author: youxun
 * @Version: 1.0
 **/
public enum RecyleEnum {


    TYPE_CNT(1,"CNT"),

    TYPE_R(2,"R积分");


    public int status;

    public String msg;


    private RecyleEnum(int status, String msg){
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
