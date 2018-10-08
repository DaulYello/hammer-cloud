package com.fmkj.user.server.service.impl;

import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.domain.UserOperateLog;
import com.fmkj.user.dao.mapper.UserLogMapper;
import com.fmkj.user.server.service.UserLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: youxun
 * @Date: 2018/8/29 13:18
 * @Description:
 */
@Service
@Transactional
public class UserLogServiceImpl extends BaseServiceImpl<UserLogMapper, UserOperateLog> implements UserLogService {
}
