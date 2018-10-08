package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcToolsMapper;
import com.fmkj.user.dao.domain.HcTools;
import com.fmkj.user.server.service.HcToolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcTools Service实现
* @Author: youxun
* @CreateDate: 2018/9/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcToolsServiceImpl extends BaseServiceImpl<HcToolsMapper, HcTools> implements HcToolsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcToolsServiceImpl.class);

}