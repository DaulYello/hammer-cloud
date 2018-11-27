package com.fmkj.user.server.service.impl;

import com.fmkj.common.annotation.BaseService;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.user.dao.mapper.PmImageMapper;
import com.fmkj.user.dao.domain.PmImage;
import com.fmkj.user.server.service.PmImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Description: PmImage Service实现
* @Author: youxun
* @CreateDate: 2018/11/21.
* @Version: 1.0
**/
@Service
@Transactional
@BaseService
public class PmImageServiceImpl extends BaseServiceImpl<PmImageMapper, PmImage> implements PmImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmImageServiceImpl.class);

    @Autowired
    private PmImageMapper pmImageMapper;


    @Override
    public List<PmImage> selectImageListByPartId(Integer partId) {
        return pmImageMapper.selectImageListByPartId(partId);
    }
}