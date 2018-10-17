package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.FmIntegralInfo;
import com.fmkj.user.dao.dto.CntRDto;
import com.fmkj.user.dao.queryVo.CntRVo;

import java.util.HashMap;
import java.util.List;

/**
* @Description: FmIntegralInfo Service接口
* @Author: youxun
* @CreateDate: 2018/10/16.
* @Version: 1.0
**/
public interface FmIntegralInfoService extends BaseService<FmIntegralInfo> {

    FmIntegralInfo getRIntegral(CntRVo cntRVo);

    CntRDto queryCNTRbyId(Integer uid);

    List<FmIntegralInfo> queryRList(HashMap<String, Object> paramMap);

    FmIntegralInfo stealRIntegral(CntRVo cntRVo);
}