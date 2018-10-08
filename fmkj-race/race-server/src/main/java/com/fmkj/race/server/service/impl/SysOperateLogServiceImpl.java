package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.SysOperateLogMapper;
import com.fmkj.race.dao.domain.SysOperateLog;
import com.fmkj.race.server.service.SysOperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: SysOperateLog Service实现
* @Author: yangshengbin
* @CreateDate: 2018/8/31.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class SysOperateLogServiceImpl extends BaseServiceImpl<SysOperateLogMapper, SysOperateLog> implements SysOperateLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysOperateLogServiceImpl.class);

}