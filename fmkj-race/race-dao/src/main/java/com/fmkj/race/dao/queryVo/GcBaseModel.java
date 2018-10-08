package com.fmkj.race.dao.queryVo;

import com.fmkj.race.dao.domain.GcActivity;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：16:24 2018/9/3 0003
 * @ Description：通知信息分页显示
 */
public class GcBaseModel extends GcActivity{
    //分页选择
    private Integer page;

    //二级菜单选择
    private Integer status;

    //竞锤合约地址
    private String contract;

    //实体属性
    private Integer id;//实体id
    private Integer uid;//用户uid

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getContract() {
        return contract;
    }

    @Override
    public void setContract(String contract) {
        this.contract = contract;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
