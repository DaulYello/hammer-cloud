package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.PmPartMapper;
import com.fmkj.user.dao.domain.PmPart;
import com.fmkj.user.server.service.PmPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
* @Description: PmPart Service实现
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmPartServiceImpl extends BaseServiceImpl<PmPartMapper, PmPart> implements PmPartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmPartServiceImpl.class);

    @Autowired
    private PmPartMapper pmPartMapper;

    @Override
    public List queryPartDynamic() {
        return pmPartMapper.queryPartDynamic();
    }

    @Override
    public HashMap queryPartByUid(Integer uid, Integer tid) {
        return pmPartMapper.queryPartByUid(uid, tid);
    }

    @Override
    public HashMap queryAuditByPartId(Integer id) {
        return pmPartMapper.queryAuditByPartId(id);
    }
}