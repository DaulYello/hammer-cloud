package com.fmkj.order.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: youxun
 * @Date: 2018/8/29 14:52
 * @Description:
 */
@TableName("fm_product_info")
public class ProductInfo extends Model<ProductInfo>{

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品编号
     */
    @TableField("product_no")
    private String productNo;
    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 商品单价
     */
    @TableField("product_price")
    private BigDecimal productPrice;
    /**
     * 出售数量
     */
    @TableField("product_sum")
    private Double productSum;
    /**
     * 库存
     */
    @TableField("product_stock")
    private Double productStock;
    /**
     * 商品图标
     */
    @TableField("product_icon")
    private String productIcon;
    /**
     * 商品状态0、新建状态; 1、正常; 2、下架; -1、删除
     */
    private Integer status;
    /**
     * 类目类型1、P能量
     */
    @TableField("category_type")
    private Integer categoryType;
    /**
     * 商品创建人
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 商品描述
     */
    @TableField("product_detail")
    private String productDetail;
    /**
     * 商品类型1、卖出;2、买入
     */
    @TableField("product_type")
    private Integer productType;
    /**
     * 币种1、人民币
     */
    private Integer currency;
    /**
     * 最高可成交的价格
     */
    @TableField("max_price")
    private BigDecimal maxPrice;
    /**
     * 每一笔交易额的最小限额
     */
    @TableField("min_limit")
    private BigDecimal minLimit;
    /**
     * 每一笔交易额的最大限额
     */
    @TableField("max_limit")
    private BigDecimal maxLimit;
    /**
     * 付款期限、默认15分钟
     */
    @TableField("payment_term")
    private Integer paymentTerm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductSum() {
        return productSum;
    }

    public void setProductSum(Double productSum) {
        this.productSum = productSum;
    }

    public Double getProductStock() {
        return productStock;
    }

    public void setProductStock(Double productStock) {
        this.productStock = productStock;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(BigDecimal minLimit) {
        this.minLimit = minLimit;
    }

    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(BigDecimal maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
