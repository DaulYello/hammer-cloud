package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.FmCntInfo;
import com.fmkj.user.dao.domain.FmIntegralInfo;
import com.fmkj.user.dao.queryVo.CntRVo;

/**
* @Description: FmCntInfo Service接口
* @Author: youxun
* @CreateDate: 2018/10/16.
* @Version: 1.0
**/
public interface FmCntInfoService extends BaseService<FmCntInfo> {

    FmCntInfo getCntByUid(CntRVo cntRVo);
}