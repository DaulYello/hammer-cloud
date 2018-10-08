package com.fmkj.user.server.enmu;


/**
 * @Description: 1、代表正面照; 2、代表反面照; 3、绑定微信4、绑定支付宝5、同时绑定微信和支付宝
 * @Author: huangs
 * @Version: 1.0
 **/
public enum ImageEnum {


    TYPE_FULL(1,"代表正面照"),

    TYPE_REVERSE(2,"代表反面照"),

    TYPE_WECHAT(3,"绑定微信"),

    TYPE_ALIPAY(4,"绑定支付宝"),

    TYPE_ALIPAYANDWECHAT(5,"同时绑定微信和支付宝");


    public int status;

    public String msg;


    private ImageEnum(int status,String msg){
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
