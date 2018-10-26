package com.fmkj.order.server.enmu;

/**
 * @Description: 商品状态  商品状态0、新建状态; 1、正常; 2、下架; -1、删除
 * @Author: youxun
 * @Version: 1.0
 **/
public enum ProductEnum {
    PRODUCT_DELETE(-1, "删除"),
    PRODUCT_ADD(0,"新建状态"),
    PRODUCT_LINE(1,"在线-已发布"),
    PRODUCT_UNLINE(2,"下架");
    public int status;
    public String msg;
    private ProductEnum(int status, String msg) {
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
