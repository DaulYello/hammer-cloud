package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.GcPremiumMapper;
import com.fmkj.race.dao.domain.GcPremium;
import com.fmkj.race.server.service.GcPremiumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: GcPremium Service实现
 * @Author: ru
 * @CreateDate: 2018/9/3.
 * @Version: 1.0
 **/
@Service
@Transactional
@BaseService
public class GcPremiumServiceImpl extends BaseServiceImpl<GcPremiumMapper, GcPremium> implements GcPremiumService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcPremiumServiceImpl.class);

    @Autowired
    private GcPremiumMapper gcPremiumMapper;

    @Override
    public GcPremium getPremiumByIntegral(Integer integral) {
        return gcPremiumMapper.getPremiumByIntegral(integral);
    }

}