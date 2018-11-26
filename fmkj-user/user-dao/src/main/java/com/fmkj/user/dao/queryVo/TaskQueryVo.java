package com.fmkj.user.dao.queryVo;

import com.fmkj.user.dao.domain.PmTask;

public class TaskQueryVo extends PmTask{

    //当前页码
    private int pageNo = 1;
    //每页条数
    private int pageSize = 20;
    // 降序字段
    private String orderBy;
    // 升序字段
    private String orderByAsc;

    private Integer uid;

    private String telephone;

    private Integer partId;

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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
