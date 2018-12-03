package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.PmExtendValueMapper;
import com.fmkj.user.dao.domain.PmExtendValue;
import com.fmkj.user.server.service.PmExtendValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: PmExtendValue Service实现
* @Author: youxun
* @CreateDate: 2018/11/27.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmExtendValueServiceImpl extends BaseServiceImpl<PmExtendValueMapper, PmExtendValue> implements PmExtendValueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmExtendValueServiceImpl.class);

    @Autowired
    private PmExtendValueMapper pmExtendValueMapper;

    @Override
    public List queryExtendList(Integer pid) {
        return pmExtendValueMapper.queryExtendList(pid);
    }
}