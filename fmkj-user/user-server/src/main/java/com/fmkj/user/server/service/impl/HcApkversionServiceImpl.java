package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcApkversionMapper;
import com.fmkj.user.dao.domain.HcApkversion;
import com.fmkj.user.server.service.HcApkversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcApkversion Service实现
* @Author: youxun
* @CreateDate: 2018/9/20.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcApkversionServiceImpl extends BaseServiceImpl<HcApkversionMapper, HcApkversion> implements HcApkversionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcApkversionServiceImpl.class);

}