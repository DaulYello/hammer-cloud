package com.fmkj.order.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: youxun
 * @Date: 2018/8/29 15:13
 * @Description:
 */
@TableName("fm_order_info")
public class OrderInfo extends Model<OrderInfo> implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;                //主键

    @TableField("product_id")
    private Integer productId;   // 商品ID

    @TableField("order_no")
    private String orderNo;    //订单编号

    @TableField("order_type")
    private Integer orderType;  // 订单类型1、买入  2、卖出

    @TableField("user_id")
    private Integer userId;   //下单用户

    @TableField("seller_id")
    private Integer sellerId;   //卖家用户

    private BigDecimal payment;  //买家支付总金额

    @TableField("payment_type")
    private Integer paymentType;  //买家支付方式1、微信; 2、支付宝

    @TableField("order_status")
    private Integer orderStatus;  // 订单状态0、未付款; 1、已付款(付款确认); 2、订单取消; 3、交易成功

    @TableField("payment_time")
    private Date paymentTime;  //支付时间

    @TableField("end_time")
    private Date endTime;      //交易完成时间

    @TableField("create_time")
    private Date createTime;  //下单时间

    @TableField("update_time")
    private Date updateTime;  //订单修改时间

    @TableField("leave_msg")
    private String leaveMsg;  //下单留言

    @TableField("is_pay")
    private Integer isPay;  //付款确认0、未付款确认;1、确认已付款

    @TableField("trade_num")
    private Double tradeNum; //交易数量,购买或卖出数量

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Double getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Double tradeNum) {
        this.tradeNum = tradeNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLeaveMsg() {
        return leaveMsg;
    }

    public void setLeaveMsg(String leaveMsg) {
        this.leaveMsg = leaveMsg;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
