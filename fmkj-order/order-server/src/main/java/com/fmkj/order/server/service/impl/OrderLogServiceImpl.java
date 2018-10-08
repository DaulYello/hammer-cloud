package com.fmkj.order.server.service.impl;

import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.order.dao.domain.OperateLog;
import com.fmkj.order.dao.mapper.OperateLogMapper;
import com.fmkj.order.server.service.OrderLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: youxun
 * @Date: 2018/8/29 13:18
 * @Description:
 */
@Service
@Transactional
public class OrderLogServiceImpl extends BaseServiceImpl<OperateLogMapper, OperateLog> implements OrderLogService {
}
