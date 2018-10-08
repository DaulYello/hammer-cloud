package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.GcMessageMapper;
import com.fmkj.race.dao.domain.GcMessage;
import com.fmkj.race.server.service.GcMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: GcMessage Service实现
* @Author: yangshengbin
* @CreateDate: 2018/8/30.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcMessageServiceImpl extends BaseServiceImpl<GcMessageMapper, GcMessage> implements GcMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcMessageServiceImpl.class);

}