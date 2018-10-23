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
     * @author ru
     * @Description:获得活动参与记录
     */
    List<JoinActivityDto>  queryJoinActivityByAid(Pagination page, JoinActivityPage joinActivityPage);

    boolean addGcJoinactivityRecord(GcJoinactivityrecord gcJoinactivityrecord, GcActivity num, String nickname);
}