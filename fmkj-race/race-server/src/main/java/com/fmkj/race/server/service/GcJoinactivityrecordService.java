package com.fmkj.race.server.service;


import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.dao.queryVo.JoinActivityPage;

import java.util.List;

/**
* @Description: GcJoinactivityrecord Service接口
* @Author: yangshengbin
* @CreateDate: 2018/9/5.
* @Version: 1.0
**/
public interface GcJoinactivityrecordService extends BaseService<GcJoinactivityrecord> {



    /**
     * @author yangshengbin
     * @Description：插入用户参与记录/更改用户cnt值/
     * @date 2018/9/6 0006 09:54
     * @param
     * @return
    */
    boolean addGcJoinactivityrecordAndUpAccount(Integer aid, GcJoinactivityrecord joins,double par);


    /**
     * @author yangshengbin
     * @Description：参加活动加载合约
     * @date 2018/9/6 0006 11:02
     * @param
     * @return
    */
    boolean participateActivity(String contract, Integer aid, Integer uid,Integer gid);



    /**
     * @author yangshengbin
     * @Description：最后一个用户参与活动
     * @date 2018/9/6 0006 14:11
     * @param
     * @return
    */
    boolean initAndloadContractAndChangeStage(String contract, Integer aid);


    /**
     * @author ru
     * @Description:获得活动参与记录
     */
    List<JoinActivityDto>  queryJoinActivityByAid(Pagination page, JoinActivityPage joinActivityPage);

    boolean addGcJoinactivityRecord(GcJoinactivityrecord gcJoinactivityrecord, GcActivity num);
}