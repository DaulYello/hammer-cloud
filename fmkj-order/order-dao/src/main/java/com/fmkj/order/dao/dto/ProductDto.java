package com.fmkj.order.dao.dto;

import com.fmkj.order.dao.domain.HcAccount;
import com.fmkj.order.dao.domain.ProductInfo;

/**
 * @Author: youxun
 * @Date: 2018/9/4 18:15
 * @Description:
 */
public class ProductDto extends ProductInfo{

    private HcAccount hcAccount;

    // 交易成功订单数量
    private Integer successNum;

    //支付宝账号
    private Integer alipayStatus;
    //微信账号
    private Integer wechatStatus;


    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public HcAccount getHcAccount() {
        return hcAccount;
    }

    public void setHcAccount(HcAccount hcAccount) {
        this.hcAccount = hcAccount;
    }

    public Integer getAlipayStatus() {
        return alipayStatus;
    }

    public void setAlipayStatus(Integer alipayStatus) {
        this.alipayStatus = alipayStatus;
    }

    public Integer getWechatStatus() {
        return wechatStatus;
    }

    public void setWechatStatus(Integer wechatStatus) {
        this.wechatStatus = wechatStatus;
    }
}
