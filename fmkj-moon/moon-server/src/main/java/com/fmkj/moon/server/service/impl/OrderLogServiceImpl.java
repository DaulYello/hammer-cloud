package com.fmkj.moon.server.service.impl;

import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.moon.dao.domain.MoonOperateLog;
import com.fmkj.moon.dao.mapper.OperateLogMapper;
import com.fmkj.moon.server.service.OrderLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: youxun
 * @Date: 2018/8/29 13:18
 * @Description:
 */
@Service
@Transactional
public class OrderLogServiceImpl extends BaseServiceImpl<OperateLogMapper, MoonOperateLog> implements OrderLogService {
}
