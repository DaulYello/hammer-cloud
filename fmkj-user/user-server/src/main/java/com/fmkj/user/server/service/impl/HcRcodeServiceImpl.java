package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcRcodeMapper;
import com.fmkj.user.dao.domain.HcRcode;
import com.fmkj.user.server.service.HcRcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcRcode Service实现
* @Author: youxun
* @CreateDate: 2018/9/14.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcRcodeServiceImpl extends BaseServiceImpl<HcRcodeMapper, HcRcode> implements HcRcodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcRcodeServiceImpl.class);

}