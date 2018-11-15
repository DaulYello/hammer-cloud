package com.fmkj.chat.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.chat.dao.mapper.FmNewsMapper;
import com.fmkj.chat.dao.domain.FmNews;
import com.fmkj.chat.server.service.FmNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: FmNews Service实现
* @Author: youxun
* @CreateDate: 2018/11/14.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class FmNewsServiceImpl extends BaseServiceImpl<FmNewsMapper, FmNews> implements FmNewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FmNewsServiceImpl.class);

}