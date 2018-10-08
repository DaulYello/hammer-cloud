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
}
