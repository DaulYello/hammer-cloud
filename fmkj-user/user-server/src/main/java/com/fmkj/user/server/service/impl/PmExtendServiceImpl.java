package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.PmExtendMapper;
import com.fmkj.user.dao.domain.PmExtend;
import com.fmkj.user.server.service.PmExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: PmExtend Service实现
* @Author: youxun
* @CreateDate: 2018/11/27.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmExtendServiceImpl extends BaseServiceImpl<PmExtendMapper, PmExtend> implements PmExtendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmExtendServiceImpl.class);

}