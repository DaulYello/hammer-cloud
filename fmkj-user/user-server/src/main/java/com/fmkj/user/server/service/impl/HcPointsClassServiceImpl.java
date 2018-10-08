package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcPointsClassMapper;
import com.fmkj.user.dao.domain.HcPointsClass;
import com.fmkj.user.server.service.HcPointsClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcPointsClass Service实现
* @Author: youxun
* @CreateDate: 2018/9/17.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcPointsClassServiceImpl extends BaseServiceImpl<HcPointsClassMapper, HcPointsClass> implements HcPointsClassService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcPointsClassServiceImpl.class);

}