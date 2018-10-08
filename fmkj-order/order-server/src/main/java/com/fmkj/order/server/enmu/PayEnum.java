package com.fmkj.order.server.enmu;

/**
 * @Description: 付款确认0、未付款确认;1、确认已付款
 * @Author: youxun
 * @Version: 1.0
 **/
public enum PayEnum {
    PAY_ADD(0,"新建状态-未付款"),
    PAY_BUYER_PAY(1,"买家已付款(付款确认)"),
    PAY_SELLER_PAY(2,"卖家已支付P能量");
    public int status;
    public String msg;
    private PayEnum(int status, String msg) {
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
