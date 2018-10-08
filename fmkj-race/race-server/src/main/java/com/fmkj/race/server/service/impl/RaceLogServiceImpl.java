package com.fmkj.race.server.service.impl;

import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.domain.RaceOperateLog;
import com.fmkj.race.dao.mapper.RaceLogMapper;
import com.fmkj.race.server.service.RaceLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: youxun
 * @Date: 2018/8/29 13:18
 * @Description:
 */
@Service
@Transactional
public class RaceLogServiceImpl extends BaseServiceImpl<RaceLogMapper, RaceOperateLog> implements RaceLogService {
}
