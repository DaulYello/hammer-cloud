package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.PmStrategyMapper;
import com.fmkj.user.dao.domain.PmStrategy;
import com.fmkj.user.server.service.PmStrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
* @Description: PmStrategy Service实现
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmStrategyServiceImpl extends BaseServiceImpl<PmStrategyMapper, PmStrategy> implements PmStrategyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmStrategyServiceImpl.class);

    @Autowired
    private PmStrategyMapper pmStrategyMapper;


    @Override
    public List queryStrategyByTid(Integer tid) {
        return pmStrategyMapper.queryStrategyByTid(tid);
    }
}