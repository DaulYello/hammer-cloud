package com.fmkj.order.server.enmu;

/**
 * @Description: 订单状态0、未付款; 1、已付款(付款确认); 2、订单取消; 3、交易成功
 * @Author: youxun
 * @Version: 1.0
 **/
public enum OrderEnum {
    ORDER_ADD(0,"新建状态-未付款"),
    ORDER_PAY(1,"已付款(付款确认)"),
    ORDER_CANCEL(2,"订单取消"),
    ORDER_SUCCESS(3,"交易成功"),
    ORDER_DEL(-1,"订单已删除");
    public int status;
    public String msg;
    private OrderEnum(int status, String msg) {
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
