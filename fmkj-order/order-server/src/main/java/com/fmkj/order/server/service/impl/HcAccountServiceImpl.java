package com.fmkj.order.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.order.dao.mapper.HcAccountMapper;
import com.fmkj.order.dao.domain.HcAccount;
import com.fmkj.order.server.service.HcAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcAccount Service实现
* @Author: youxun
* @CreateDate: 2018/9/3.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcAccountServiceImpl extends BaseServiceImpl<HcAccountMapper, HcAccount> implements HcAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcAccountServiceImpl.class);

}