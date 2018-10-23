package com.fmkj.race.dao.queryVo;

import com.fmkj.race.dao.domain.GcJoinactivityrecord;

/**
 * 活动参与记录
 */
public class JoinActivityPage extends GcJoinactivityrecord {

    //分页选择
    private Integer page;

    //活动aid
    private Integer aid;

    //当前用户uid
    private Integer uid;

    private String nickname;

    //当前页码
    private int pageNo = 1;
    //每页条数
    private int pageSize = 20;
    // 降序字段
    private String orderBy;
    // 升序字段
    private String orderByAsc;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getAid() {
        return aid;
    }

    @Override
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    @Override
    public Integer getUid() {
        return uid;
    }

    @Override
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
