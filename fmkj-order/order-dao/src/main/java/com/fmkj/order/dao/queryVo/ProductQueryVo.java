package com.fmkj.order.dao.queryVo;

import com.fmkj.order.dao.domain.ProductInfo;

/**
 * @Author: youxun
 * @Date: 2018/8/31 16:50
 * @Description:
 */
public class ProductQueryVo extends ProductInfo{
    //当前页码
    private int pageNo = 1;
    //每页条数
    private int pageSize = 20;
    // 降序字段
    private String orderBy;
    // 升序字段
    private String orderByAsc;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByAsc() {
        return orderByAsc;
    }

    public void setOrderByAsc(String orderByAsc) {
        this.orderByAsc = orderByAsc;
    }
}
