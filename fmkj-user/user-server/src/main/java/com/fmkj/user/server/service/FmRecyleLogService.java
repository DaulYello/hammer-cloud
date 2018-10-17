package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.FmRecyleLog;

import java.util.List;

/**
* @Description: FmRecyleLog Service接口
* @Author: youxun
* @CreateDate: 2018/10/16.
* @Version: 1.0
**/
public interface FmRecyleLogService extends BaseService<FmRecyleLog> {

    List queryCntDynamic(Integer uid);

    List queryRankList();
}