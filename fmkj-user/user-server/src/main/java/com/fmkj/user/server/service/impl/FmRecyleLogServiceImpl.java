package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.FmRecyleLogMapper;
import com.fmkj.user.dao.domain.FmRecyleLog;
import com.fmkj.user.server.service.FmRecyleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: FmRecyleLog Service实现
* @Author: youxun
* @CreateDate: 2018/10/16.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class FmRecyleLogServiceImpl extends BaseServiceImpl<FmRecyleLogMapper, FmRecyleLog> implements FmRecyleLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FmRecyleLogServiceImpl.class);

    @Autowired
    private FmRecyleLogMapper fmRecyleLogMapper;

    @Override
    public List queryCntDynamic(Integer uid) {
        return fmRecyleLogMapper.queryCntDynamic(uid);
    }

    @Override
    public List queryRankList() {
        return fmRecyleLogMapper.queryRankList();
    }
}