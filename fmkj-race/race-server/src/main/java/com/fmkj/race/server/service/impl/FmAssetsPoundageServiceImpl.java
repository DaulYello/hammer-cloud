package com.fmkj.race.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.race.dao.mapper.FmAssetsPoundageMapper;
import com.fmkj.race.dao.domain.FmAssetsPoundage;
import com.fmkj.race.server.service.FmAssetsPoundageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FmAssetsPoundageServiceImpl extends BaseServiceImpl<FmAssetsPoundageMapper, FmAssetsPoundage> implements FmAssetsPoundageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FmAssetsPoundageServiceImpl.class);

}