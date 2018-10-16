package com.fmkj.order.dao.dto;

import com.fmkj.order.dao.domain.HcAccount;
import com.fmkj.order.dao.domain.OrderInfo;

/**
 * @Author: youxun
 * @Date: 2018/9/4 18:08
 * @Description: 返回前端对象
 */
public class OrderDto extends OrderInfo{

    // 下单用户信息
    private HcAccount hcAccount;
    // 用户下单时支付使用的账号
    private String payAccount;
    // 用户下单时支付使用的二维码
    private String payCode;

    public HcAccount getHcAccount() {
        return hcAccount;
    }

    public void setHcAccount(HcAccount hcAccount) {
        this.hcAccount = hcAccount;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
