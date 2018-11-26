package com.fmkj.user.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.user.dao.domain.PmStrategy;

import java.util.HashMap;
import java.util.List;

/**
* @Description: PmStrategy Service接口
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
public interface PmStrategyService extends BaseService<PmStrategy> {

    List queryStrategyByTid(Integer tid);

    List queryPromptByTid(Integer tid);
}