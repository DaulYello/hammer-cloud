package com.fmkj.race.server.service;

import com.fmkj.common.base.BaseService;
import com.fmkj.race.dao.domain.GcPremium;

/**
* @Description: GcPremium Service接口
* @Author: ru
* @CreateDate: 2018/9/3.
* @Version: 1.0
**/
public interface GcPremiumService extends BaseService<GcPremium> {
    public GcPremium getPremiumByIntegral(Integer integral);

}