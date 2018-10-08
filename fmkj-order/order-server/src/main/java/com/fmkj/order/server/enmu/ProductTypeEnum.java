package com.fmkj.order.server.enmu;

/**
 * @Description: 商品类型1、卖出;2、买入
 * @Author: youxun
 * @Version: 1.0
 **/
public enum ProductTypeEnum {
    SELL_TYPE(1,"卖出"),
    BUY_TYPE(2,"买入");
    public int status;
    public String msg;
    private ProductTypeEnum(int status, String msg) {
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
