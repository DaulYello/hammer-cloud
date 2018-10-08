package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcFeedbackMapper;
import com.fmkj.user.dao.domain.HcFeedback;
import com.fmkj.user.server.service.HcFeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcFeedback Service实现
* @Author: youxun
* @CreateDate: 2018/9/20.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcFeedbackServiceImpl extends BaseServiceImpl<HcFeedbackMapper, HcFeedback> implements HcFeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcFeedbackServiceImpl.class);

}