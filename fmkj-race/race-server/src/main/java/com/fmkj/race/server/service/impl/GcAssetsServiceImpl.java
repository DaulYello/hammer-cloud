package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.GcAssetsMapper;
import com.fmkj.race.dao.domain.GcAssets;
import com.fmkj.race.server.service.GcAssetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: GcAssets Service实现
* @Author: youxun
* @CreateDate: 2018/10/17.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class GcAssetsServiceImpl extends BaseServiceImpl<GcAssetsMapper, GcAssets> implements GcAssetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcAssetsServiceImpl.class);

}