package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcMoonwellMapper;
import com.fmkj.user.dao.domain.HcMoonwell;
import com.fmkj.user.server.service.HcMoonwellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcMoonwell Service实现
* @Author: youxun
* @CreateDate: 2018/9/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcMoonwellServiceImpl extends BaseServiceImpl<HcMoonwellMapper, HcMoonwell> implements HcMoonwellService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcMoonwellServiceImpl.class);

}