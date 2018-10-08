package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.HcAssetsMapper;
import com.fmkj.user.dao.domain.HcAssets;
import com.fmkj.user.server.service.HcAssetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description: HcAssets Service实现
* @Author: youxun
* @CreateDate: 2018/9/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class HcAssetsServiceImpl extends BaseServiceImpl<HcAssetsMapper, HcAssets> implements HcAssetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcAssetsServiceImpl.class);

    @Autowired
    private HcAssetsMapper assetsMapper;

    public boolean updateAssetsContent(HcAssets assets){

        return assetsMapper.updateAssetsContent(assets)>0 ? true:false;
    }
}